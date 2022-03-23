package com.example.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class metincozumleme extends Fragment {

   public EditText editText;
    private Button analysis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_metincozumleme, container, false);
        editText= v.findViewById(R.id.editTextTextMultiLine);
        analysis=v.findViewById(R.id.analysis);

        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText==null)
                {
                    Toast.makeText(getActivity(),"Please enter content",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent=new Intent(getActivity(),SelectActivity.class);
                    intent.putExtra("key",setEditText());
                    startActivity(intent);

                }

            }
        });

        return v;
    }

    public String setEditText()
    {

        return  editText.getText().toString()+",";

    }


}
