package com.jeimandei.projectone;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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

    EditText p_id, p_name, p_email, p_phone;
    Button update, delete;
    String id, id_comp;
    ViewGroup view;
    String JSON_STRING, p_compid;
    Spinner p_company;

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


        update = view.findViewById(R.id.update_participant);
        delete = view.findViewById(R.id.delete_participant);

        String a = this.getArguments().getString("id");
        Config.PARTICIPANT_ID = a;

        Log.d("Ar: ", Config.PARTICIPANT_ID);
        id = a;
        p_id.setText(a);

        getJSONComp();
        p_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                p_compid = String.valueOf(p_company.getSelectedItemId()+1);
                Log.d("qwe:", p_compid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getJSON();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = p_name.getText().toString().trim();
                final String email = p_email.getText().toString().trim();
                final String phone = p_phone.getText().toString().trim();

                class UpdateData extends AsyncTask<Void, Void, String>{

                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(getContext(), "Changing Data", "Please Wait...", false, false);
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(Config.KEY_ID_PARTICIPANT, id);
                        params.put(Config.KEY_NAME_PARTICIPANT, name);
                        params.put(Config.KEY_EMAIL_PARTICIPANT, email);
                        params.put(Config.KEY_PHONE_PARTICIPANT, phone);
                        params.put(Config.KEY_COMPANY_PARTICIPANT, p_compid);

                        Log.d("in", name);
                        Log.d("inputss", String.valueOf(params));
                        HttpHandler handler = new HttpHandler();
                        String res = handler.sendPostReq(Config.URL_UPDATE_PARTICIPANT, params);

                        return res;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

                        ParticipantFragment participantFragment = new ParticipantFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,participantFragment);
                        fragmentTransaction.commit();
                    }
                }
                UpdateData updateData = new UpdateData();
                updateData.execute();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Participant?");
                builder.setMessage("Are you sure?");
                builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
                builder.setCancelable(false);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteData();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

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
            String company = object.getString(Config.TAG_JSON_COMPANYID_PARTICIPANT);

            p_name.setText(name);
            p_email.setText(email);
            p_phone.setText(phone);
            p_company.setSelection(Integer.parseInt(company)-1);
            Log.d("CekNama", json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deleteData() {
        class Delete extends AsyncTask<Void, Void, String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Deleting Data", "Please Wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String res = handler.sendGetResp(Config.URL_DELETE_PARTICIPANT, id);
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getContext(), "Participant Deleted!!!", Toast.LENGTH_SHORT).show();

                ParticipantFragment participantFragment = new ParticipantFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,participantFragment);
                fragmentTransaction.commit();
            }
        }
        Delete delete = new Delete();
        delete.execute();
    }

    private void getJSONComp() {
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
                        p_compid = id;


                        arrayList.add(name);
                        Log.d("DataArr: ", String.valueOf(name));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                p_company.setAdapter(adapter);
                Log.d("spin", String.valueOf(arrayList));
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
}