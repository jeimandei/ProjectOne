package com.jeimandei.projectone;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText c_id, c_name, c_address;
    Button update, delete;
    String id;
    ViewGroup view;

    public CompanyDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompanyDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompanyDetailFragment newInstance(String param1, String param2) {
        CompanyDetailFragment fragment = new CompanyDetailFragment();
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_company_detail, container, false);
        c_id = view.findViewById(R.id.detail_id_company);
        c_name = view.findViewById(R.id.detail_name_company);
        c_address = view.findViewById(R.id.detail_address_company);
        update = view.findViewById(R.id.update_company);
        delete = view.findViewById(R.id.delete_company);

        String a = this.getArguments().getString("id");
        Config.COMPANY_ID = a;

        Log.d("Ar: ", Config.COMPANY_ID);
        id = a;
        c_id.setText(a);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name =  c_name.getText().toString().trim();
                final String address =  c_address.getText().toString().trim();

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
                        params.put(Config.KEY_ID_COMPANY, id);
                        params.put(Config.KEY_NAME_COMPANY, name);
                        params.put(Config.KEY_ADDRESS_COMPANY, address);
                        Log.d("in", name);
                        Log.d("inputss", String.valueOf(params));
                        HttpHandler handler = new HttpHandler();
                        String res = handler.sendPostReq(Config.URL_UPDATE_COMPANY, params);

                        return res;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

                        CompanyFragment companyFragment = new CompanyFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,companyFragment);
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
                builder.setTitle("Delete Company?");
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
                String result = handler.sendGetResp(Config.URL_GET_DETAIL_COMPANY, id);
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
            JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_COMPANY);
            JSONObject object = jsonArray.getJSONObject(0);

            String name = object.getString(Config.TAG_JSON_NAME_COMPANY);
            String address = object.getString(Config.TAG_JSON_ADDRESS_COMPANY);
            c_name.setText(name);
            c_address.setText(address);
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
                String res = handler.sendGetResp(Config.URL_DELETE_COMPANY, id);
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getContext(), "Company Deleted!!!", Toast.LENGTH_SHORT).show();

                SubjectFragment subjectFragment = new SubjectFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,subjectFragment);
                fragmentTransaction.commit();
            }
        }
        Delete delete = new Delete();
        delete.execute();
    }
}