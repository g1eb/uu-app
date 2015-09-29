package nl.utrecht.uni.questions.questions;

import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by Gleb on 10/11/14.
 */
public class LabelPrinter extends AsyncTask<URL, Integer, String> {

    String question;

    public LabelPrinter(String question) {
        this.question = question;
    }

    @Override
    protected String doInBackground(URL... params) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
