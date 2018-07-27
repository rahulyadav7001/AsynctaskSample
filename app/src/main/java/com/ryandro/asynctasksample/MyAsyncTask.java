package com.ryandro.asynctasksample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<String, Integer, Integer> {
    private Context context;
    ProgressDialog dialog;

    public MyAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("Asyntask Thread ID", "OnPreExecution - " + Thread.currentThread().getId());
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setProgress(0);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
//                myAsyncTask.cancel(true);
//                onCancelled();
                cancel(true);
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        // task performance will here
        Log.d("Asyntask Thread ID", "onDoInBackground - " + Thread.currentThread().getId());
        int time = Integer.parseInt(strings[0]);
        int progressStatus = 0;
        for (int i = 0; i < 10; i++) {
            progressStatus = i * 10;
            if (isCancelled()) {
                break;
            } else {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(progressStatus);
                Log.d("Asyntask Thread", "" + progressStatus);
            }

        }
        return progressStatus;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        dialog.setProgress(values[0]);

    }

    @Override
    protected void onPostExecute(Integer s) {
        super.onPostExecute(s);
        Log.d("Asyntask Thread ID", "onPostExecution - " + Thread.currentThread().getId());
        dialog.cancel();
        Toast.makeText(context, "Your Task Has been Done", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d("Asyntask Thread", "onCancelled - " + Thread.currentThread().getId());


    }

    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
        Log.d("Asyntask Thread", "onCancelled with Integer value - " + integer + " & thread ID " + Thread.currentThread().getId());
    }

}
