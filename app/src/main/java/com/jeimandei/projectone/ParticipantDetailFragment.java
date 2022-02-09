package com.jeimandei.projectone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParticipantDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParticipantDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText p_id, p_name, p_email, p_phone, p_company;
    Button update, delete;
    String id;
    ViewGroup view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ParticipantDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParticipantDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParticipantDetailFragment newInstance(String param1, String param2) {
        ParticipantDetailFragment fragment = new ParticipantDetailFragment();
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_participant_detail, container, false);

        p_id = view.findViewById(R.id.detail_id_participant);
        p_name = view.findViewById(R.id.detail_name_participant);
        p_email = view.findViewById(R.id.detail_email_participant);
        p_phone = view.findViewById(R.id.detail_phone_participant);
        p_company = view.findViewById(R.id.detail_company_participant);

        String a = this.getArguments().getString("id");
        Config.PARTICIPANT_ID = a;

        Log.d("Ar: ", Config.PARTICIPANT_ID);
        id = a;
        p_id.setText(a);

        getJSON();

        return view;
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
                String result = handler.sendGetResp(Config.URL_GET_DETAIL_PARTICIPANT, id);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                displayDetailData(s);

                Log.d("CekNama", s);

            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_PARTICIPANT);
            JSONObject object = jsonArray.getJSONObject(0);

            String name = object.getString(Config.TAG_JSON_NAME_PARTICIPANT);
            String email = object.getString(Config.TAG_JSON_EMAIL_PARTICIPANT);
            String phone = object.getString(Config.TAG_JSON_PHONE_PARTICIPANT);
            String company = object.getString(Config.TAG_JSON_COMPANY_PARTICIPANT);

            p_name.setText(name);
            p_email.setText(email);
            p_phone.setText("+" + phone.trim());
            p_company.setText(company);
            Log.d("CekNama", json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}