package it.uniba.pioneers.testtool.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.uniba.pioneers.testtool.R;

public class FragmentHomeVisitatore extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHomeVisitatore() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHomeVisitatore.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHomeVisitatore newInstance(String param1, String param2) {
        FragmentHomeVisitatore fragment = new FragmentHomeVisitatore();
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
        return inflater.inflate(R.layout.fragment_home_visitatore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // setTextEditText();
    }

   /* private void setTextEditText(){

        TextView visitePredef = getActivity().findViewById(R.id.visite_predefinite);
        TextView tueVisite =  getActivity().findViewById(R.id.le_tue_visite);
        TextView creaVisita = getActivity().findViewById(R.id.crea_visita);
        TextView interagisciOpera =  getActivity().findViewById(R.id.scanner_qr);
        EditText text_cerca = (EditText) getActivity().findViewById(R.id.text_cerca);

        text_cerca.setHint(R.string.text_cerca);
        visitePredef.setText(R.string.btn_visite_predefinite);
        tueVisite.setText(R.string.btn_tue_visite);
        creaVisita.setText(R.string.btn_crea_visita);
        interagisciOpera.setText(R.string.text_interagisci);

    }*/
}