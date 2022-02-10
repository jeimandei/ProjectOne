package com.jeimandei.projectone;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddParticipantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddParticipantFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button add;
    private EditText p_name, p_email, p_phone;
    private Spinner p_comp;
    private ViewGroup viewGroup;
    private String JSON_STRING, p_comp_id;

    public AddParticipantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddParticipantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddParticipantFragment newInstance(String param1, String param2) {
        AddParticipantFragment fragment = new AddParticipantFragment();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_add_participant, container, false);

        p_name = viewGroup.findViewById(R.id.add_name_participant);
        p_email = viewGroup.findViewById(R.id.add_email_participant);
        p_phone = viewGroup.findViewById(R.id.add_phone_participant);
        add = viewGroup.findViewById(R.id.add_participant);
        p_comp = (Spinner) viewGroup.findViewById(R.id.spinner_par_comp);

        getJSON();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = p_name.getText().toString().trim();
                String email = p_email.getText().toString().trim();
                String phone = p_phone.getText().toString().trim();

                class SaveData extends AsyncTask<Void, Void, String> {
                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(getContext(), "Saving Data", "Please Wait...", false, false);
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(Config.KEY_COMPANY_PARTICIPANT, p_comp_id);
                        params.put(Config.KEY_PHONE_PARTICIPANT, phone);
                        params.put(Config.KEY_EMAIL_PARTICIPANT, email);
                        params.put(Config.KEY_NAME_PARTICIPANT, name);
                        Log.d("inputss", String.valueOf(params));
                        HttpHandler handler = new HttpHandler();
                        String res = handler.sendPostReq(Config.URL_ADD_PARTICIPANT, params);
                        return res;
                    }

                    @Override
                    protected void onPostExecute(String messsage) {
                        super.onPostExecute(messsage);
                        loading.dismiss();
                        Toast.makeText(getContext(), "Participant Added", Toast.LENGTH_LONG).show();
                        Log.d("m:", messsage);
                        clearText();

                        ParticipantFragment participantFragment = new ParticipantFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,participantFragment);
                        fragmentTransaction.commit();
                    }
                }
                SaveData saveData = new SaveData();
                saveData.execute();
            }
        });

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
                String result = handler.sendGetResp(Config.URL_GET_ALL_COMPANY);
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
                }, 1500);

                JSON_STRING = s;
                Log.d("Data_JSON", JSON_STRING);

                JSONObject jsonObject = null;
                ArrayList<String> arrayList = new ArrayList<>();

                try {
                    jsonObject = new JSONObject(JSON_STRING);
                    JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_COMPANY);
                    Log.d("ass", String.valueOf(jsonArray));
                    Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


                    for (int i=0;i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString(Config.TAG_JSON_ID_COMPANY);
                        String name = object.getString(Config.TAG_JSON_NAME_COMPANY);
                        p_comp_id = id;

                        arrayList.add(name);
                        Log.d("DataArr: ", String.valueOf(name));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                p_comp.setAdapter(adapter);
                Log.d("spin", String.valueOf(arrayList));
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void clearText() {
        p_name.setText("");
        p_email.setText("");
        p_phone.setText("");
        p_name.requestFocus();
    }
}