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

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSubjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSubjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button add;
    private EditText s_name;
    private ViewGroup viewGroup;

    public AddSubjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSubjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddSubjectFragment newInstance(String param1, String param2) {
        AddSubjectFragment fragment = new AddSubjectFragment();
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
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_add_subject, container, false);

        s_name = viewGroup.findViewById(R.id.add_name_subject);
        add = viewGroup.findViewById(R.id.add_subject);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_s = s_name.getText().toString().trim();

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
                        params.put(Config.KEY_NAME_SUBJECT, name_s);
                        Log.d("inputss", String.valueOf(params));
                        HttpHandler handler = new HttpHandler();
                        String res = handler.sendPostReq(Config.URL_ADD_SUBJECT, params);
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
                    }
                }
                SaveData saveData = new SaveData();
                saveData.execute();
            }
        });

        return viewGroup;
    }
    private void clearText() {
        s_name.setText("");
        s_name.requestFocus();
    }
}