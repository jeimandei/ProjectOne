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
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String JSON_STRING;
    private ViewGroup viewGroup;
    private ListView lv_part;
    private FloatingActionButton add;


    public CompanyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompanyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompanyFragment newInstance(String param1, String param2) {
        CompanyFragment fragment = new CompanyFragment();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_company, container, false);

        add = viewGroup.findViewById(R.id.company_save);
        lv_part = (ListView) viewGroup.findViewById(R.id.lv_company);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCompanyFragment addCompanyFragment = new AddCompanyFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,addCompanyFragment);
                callFragment(addCompanyFragment);
                fragmentTransaction.commit();
            }
        });

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
                }, 2000);

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
            JSONArray jsonArray = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY_COMPANY);
            Log.d("Data_JSON_LIST: ", String.valueOf(jsonArray));


            for (int i=0;i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString(Config.TAG_JSON_ID_COMPANY);
                String name = object.getString(Config.TAG_JSON_NAME_COMPANY);
                String address = object.getString(Config.TAG_JSON_ADDRESS_COMPANY);

                HashMap<String, String> company = new HashMap<>();
                company.put(Config.TAG_JSON_ID_COMPANY, id);
                company.put(Config.TAG_JSON_NAME_COMPANY, name);
                company.put(Config.TAG_JSON_ADDRESS_COMPANY, address);

                arrayList.add(company);
                Log.d("DataArr: ", String.valueOf(company));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                viewGroup.getContext(), arrayList, R.layout.lv_company,
                new String[] {Config.TAG_JSON_ID_COMPANY, Config.TAG_JSON_NAME_COMPANY, Config.TAG_JSON_ADDRESS_COMPANY},
                new int[] {R.id.lv_company_id, R.id.lv_company_name, R.id.lv_company_address}
        );
        Log.d("DataArray: ", String.valueOf(adapter));
        lv_part.setAdapter(adapter);


        lv_part.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                CompanyDetailFragment companyDetailFragment = new CompanyDetailFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout,companyDetailFragment);


                HashMap<String, String> map = (HashMap) adapterView.getItemAtPosition(i);
                String companyid = map.get(Config.TAG_JSON_ID_COMPANY).toString();
                Bundle args = new Bundle();
                args.putString("id", companyid);
                companyDetailFragment.setArguments(args);



                Log.d("Par: ", String.valueOf(args));
                fragmentTransaction.commit();
            }
        });
    }

    public void callFragment(Fragment fragment) {
        FragmentManager man = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = man.beginTransaction();
        trans.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
        );
        trans.replace(R.id.framelayout, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }
}