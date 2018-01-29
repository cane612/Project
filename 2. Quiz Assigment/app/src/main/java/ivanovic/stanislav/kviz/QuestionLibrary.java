package ivanovic.stanislav.kviz;

public class QuestionLibrary {

    public String Questions[] = {
            "How to Get a response from an activity in Android?",
            "__________ sets the gravity of the View or Layout in its parent?",
            "The root element of the AndroidManifest.xml file is?",
            "What is ANR in android?",
            "On which Thread Services work in Android?"
    };


    public String QuestionsOptions[][] = {
            {"startActivityForResult()","startActivityToResult()","Bundle()"},
            {"android:gravity","android:layout_gravity","android:weight"},
            {"Activity","Action","Manifest"},
            {"Application Not Responding","Android Not Responding","None of the above"},
            {"Own Thread","Main Thread","Background Thread"},
    };

    public int CorrectAnswers[]={1,2,3,1,2};

    public String getQuestion(int number){
        return Questions[number];
    }

    public String getChoice1(int questionNumber ){
        return QuestionsOptions[questionNumber][0];
    }
    public String getChoice2(int questionNumber ){
        return QuestionsOptions[questionNumber][1];
    }
    public String getChoice3(int questionNumber ){
        return QuestionsOptions[questionNumber][2];
    }

    public int getCorrectAnswerPosition(int questionNumber){
        return CorrectAnswers[questionNumber];
    }
}
