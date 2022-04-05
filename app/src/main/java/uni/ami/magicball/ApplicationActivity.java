package uni.ami.magicball;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.Random;

import uni.ami.magicball.utils.Answer;

public class ApplicationActivity extends AppCompatActivity implements View.OnClickListener {
    Button answerQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        answerQuestionButton = findViewById(R.id.answerQuestionButton);
        answerQuestionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        answerQuestionButton.setText(getRandomAnswer());
    }

    private String getRandomAnswer() {
        List<Answer> givenList = Answer.asList();
        Random rand = new Random();
        return givenList.get(rand.nextInt(givenList.size())).getValue();
    }
}