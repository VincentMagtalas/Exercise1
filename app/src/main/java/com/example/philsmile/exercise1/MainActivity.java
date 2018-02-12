package com.example.philsmile.exercise1;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.philsmile.exercise1.Adapters.UsersRecyclerViewAdapter;
import com.example.philsmile.exercise1.Classes.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String TAG = "LOL";
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.users_rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        new GetUsersAync().execute(this);

    }

    private class GetUsersAync extends AsyncTask<Context, Void, ArrayList<User>> {

        private String TAG = "LOL";
        private Context contx;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<User> doInBackground(Context... params) {
            contx = params[0];
            Log.e(TAG, "start aynctask to get coupons");

            try {
                return getHTTP();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<User> userList) {
            Log.i(TAG,"need na display");

            if(userList != null){
                Log.e(TAG, "populate UI recycler view with gson converted data");

                UsersRecyclerViewAdapter couponsRecyclerViewAdapter = new UsersRecyclerViewAdapter(userList,contx);
                rv.setAdapter(couponsRecyclerViewAdapter);
            }


        }
    }

    public ArrayList<User> getHTTP() throws IOException {

        String stringUrl = "https://jsonplaceholder.typicode.com/users";
        String inputLine;

        final String REQUEST_METHOD = "GET";
        final int READ_TIMEOUT = 15000;
        final int CONNECTION_TIMEOUT = 15000;
        //Create a URL object holding our url
        URL myUrl = new URL(stringUrl);

        //Create a connection
        HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();

        //Set methods and timeouts
        connection.setRequestMethod(REQUEST_METHOD);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);

        //Connect to our url
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create a new InputStreamReader
        InputStreamReader streamReader = new
                InputStreamReader(connection.getInputStream());

        //Create a new buffered reader and String Builder
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder stringBuilder = new StringBuilder();

        //Check if the line we are reading is not null
        while((inputLine = reader.readLine()) != null){
            stringBuilder.append(inputLine);
        }

        //Close our InputStream and Buffered reader
        reader.close();
        streamReader.close();

        //Set our result equal to our stringBuilder
        String result = stringBuilder.toString();

        Log.i(TAG,result);

        return parseUser(result);

    }

    public ArrayList<User> parseUser(String result){

        Gson gson = new Gson();
        Type listType = new TypeToken<List<User>>(){}.getType();
        ArrayList<User> userList = (ArrayList<User>) gson.fromJson(result, listType);

        return userList;
    }


}
