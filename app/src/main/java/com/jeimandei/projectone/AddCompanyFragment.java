package com.jeimandei.projectone;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCompanyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCompanyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button add;
    private EditText c_name, c_address;
    private ViewGroup viewGroup;

    public AddCompanyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCompanyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCompanyFragment newInstance(String param1, String param2) {
        AddCompanyFragment fragment = new AddCompanyFragment();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_add_company, container, false);
        c_name = viewGroup.findViewById(R.id.add_name_company);
        c_address = viewGroup.findViewById(R.id.add_address_company);
        add = viewGroup.findViewById(R.id.add_company);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = c_name.getText().toString().trim();
                String address = c_address.getText().toString().trim();

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
                        params.put(Config.KEY_NAME_COMPANY, name);
                        params.put(Config.KEY_ADDRESS_COMPANY, address);
                        Log.d("inputss", String.valueOf(params));
                        HttpHandler handler = new HttpHandler();
                        String res = handler.sendPostReq(Config.URL_ADD_COMPANY, params);
                        return res;
                    }

                    @Override
                    protected void onPostExecute(String messsage) {
                        super.onPostExecute(messsage);
                        loading.dismiss();
                        Toast.makeText(getContext(), "Data Added Successfully...", Toast.LENGTH_LONG).show();
                        clearText();

                        SubjectFragment subjectFragment = new SubjectFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout,subjectFragment);
                        fragmentTransaction.commit();
                    }
                }
                SaveData saveData = new SaveData();
                saveData.execute();
            }
        });

        return viewGroup;
    }

    private void clearText() {
        c_name.setText("");
        c_address.setText("");
        c_name.requestFocus();
    }
}