package uni.ami.magicball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.List;
import java.util.Random;

import uni.ami.magicball.utils.Answer;

public class ApplicationActivity extends AppCompatActivity implements View.OnClickListener {

    Button answerQuestionButton;

    private final Random rand = new Random();
    private final List<Answer> givenList = Answer.asList();
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 2000;
    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

//    final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
//            float x = sensorEvent.values[0];
//            float y = sensorEvent.values[1];
//            float z = sensorEvent.values[2];
//
//            accelerationCurrentValue = Math.sqrt( (x * x + y * y + z * z) );
//            double changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
//            accelerationPreviousValue = accelerationCurrentValue;
//
//            if (changeInAcceleration > 11) {
//                showAnswer();
//            }
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 200) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    showAnswer();
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        answerQuestionButton = findViewById(R.id.answerQuestionButton);
        answerQuestionButton.setOnClickListener(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onClick(View view) {
        showAnswer();
    }

    private void showAnswer() {
        answerQuestionButton.startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake));
        Answer answer = getRandomAnswer();
        answerQuestionButton.setText(answer.getValue());
        if (answer == Answer.NO) {
            answerQuestionButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        } else if (answer == Answer.YES) {
            answerQuestionButton.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        } else {
            answerQuestionButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private Answer getRandomAnswer() {
        return givenList.get(rand.nextInt(givenList.size()));
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}