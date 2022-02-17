package it.uniba.pioneers.testtool.AreaPersonale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import it.uniba.pioneers.testtool.MainActivity;
import it.uniba.pioneers.testtool.R;


public class FragmentAreaPersonaleVisitatore extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragmentAreaPersonaleVisitatore() {

    }

    public static FragmentAreaPersonaleVisitatore newInstance(String param1, String param2) {
        FragmentAreaPersonaleVisitatore fragment = new FragmentAreaPersonaleVisitatore();
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
        return inflater.inflate(R.layout.fragment_area_personale_visitatore, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDataVisitatore();
    }

    @Override
    public void onResume() {
        super.onResume();
        setDataVisitatore();
    }
    //Metodo necessario per impostare i dati del Visitatore all'interno dell'Area Personale
    private void setDataVisitatore(){
        ImageView propic = (ImageView) getActivity().findViewById(R.id.img_propic);
        EditText nome = (EditText) getActivity().findViewById(R.id.txt_nome);
        EditText cognome = (EditText) getActivity().findViewById(R.id.txt_cognome);
        EditText datanascita = (EditText) getActivity().findViewById(R.id.txt_datan);
        EditText email = (EditText) getActivity().findViewById(R.id.txt_email);

        if(MainActivity.visitatore.getPropic() != null) {
            byte[] bytes = Base64.decode(MainActivity.visitatore.getPropic(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            propic.setImageBitmap(decodedByte);
            propic.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        nome.setText(MainActivity.visitatore.getNome());
        cognome.setText(MainActivity.visitatore.getCognome());
        email.setText(MainActivity.visitatore.getEmail());
        datanascita.setText(MainActivity.visitatore.getShorterDataNascita());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}