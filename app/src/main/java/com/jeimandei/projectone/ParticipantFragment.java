package com.jeimandei.projectone;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import com.jeimandei.projectone.databinding.FragmentParticipantBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParticipantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParticipantFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String JSON_STRING;
    private ViewGroup viewGroup;
    private ListView lv_part;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ParticipantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParticipantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParticipantFragment newInstance(String param1, String param2) {
        ParticipantFragment fragment = new ParticipantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_participant, container, false);

        lv_part = (ListView) viewGroup.findViewById(R.id.lv_participant);
        getJSON();

        return viewGroup;
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(getContext(), "Getting Data", "Please wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResp(Config.URL_GET_ALL_PARTICIPANT);
                Log.d("GetData", result);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);

                JSON_STRING = s;
                Log.d("Data_JSON", JSON_STRING);

                displayAllParticipant();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllParticipant() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_PARTICIPANT);
            Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


            for (int i=0;i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(Config.TAG_JSON_ID_PARTICIPANT);
                String name = object.getString(Config.TAG_JSON_NAME_PARTICIPANT);
                String email = object.getString(Config.TAG_JSON_EMAIL_PARTICIPANT);
                String phone = object.getString(Config.TAG_JSON_PHONE_PARTICIPANT);

                HashMap<String, String> participant = new HashMap<>();
                participant.put(Config.TAG_JSON_ID_PARTICIPANT, id);
                participant.put(Config.TAG_JSON_NAME_PARTICIPANT, name);
                participant.put(Config.TAG_JSON_EMAIL_PARTICIPANT, email);
                participant.put(Config.TAG_JSON_PHONE_PARTICIPANT, phone);

                arrayList.add(participant);
                Log.d("DataArr: ", String.valueOf(participant));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                viewGroup.getContext(), arrayList, R.layout.lv_participant,
                new String[] {Config.TAG_JSON_ID_PARTICIPANT, Config.TAG_JSON_NAME_PARTICIPANT, Config.TAG_JSON_EMAIL_PARTICIPANT,Config.TAG_JSON_PHONE_PARTICIPANT},
                new int[] {R.id.lv_participant_id, R.id.lv_participant_name, R.id.lv_participant_email, R.id.lv_participant_phone}
        );
        Log.d("DataArray: ", String.valueOf(adapter));
        lv_part.setAdapter(adapter);
    }
}