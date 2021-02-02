package com.example.bloodbank;

public class QuestionLibrary {

     private String mQuestions [] = {
            "Who developed Java Language?",
            "In which Year Java was Created?",
            "Who is known as Father of Java?",
            "Syntax of Java is similar to which language?",
             "-"

    };


     private String mChoices [][] = {
            {"Oracle", "Sun Microsystem", "Bell Labs"},
            {"1992", "1989", "1995"},
            {"Bill Gates", "Denis Ritchie", "James Gosling"},
            {"C/C++", "Python", "C#"},
             {".",".","."}
    };



     public String mCorrectAnswers[] = {"Sun Microsystem", "1995", "James Gosling", "C/C++","."};




    public String getQuestion(int a) {
        String question = mQuestions[a];
        return question;
    }


    public String getChoice1(int a) {
        String choice0 = mChoices[a][0];
        return choice0;
    }


    public String getChoice2(int a) {
        String choice1 = mChoices[a][1];
        return choice1;
    }

    public String getChoice3(int a) {
        String choice2 = mChoices[a][2];
        return choice2;
    }

    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswers[a];
        return answer;
    }
}

