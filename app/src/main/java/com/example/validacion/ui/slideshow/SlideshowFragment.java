package com.example.validacion.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.validacion.databinding.FragmentSlideshowBinding;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        final Button btnValidar = binding.btnValidar;
        btnValidar.setOnClickListener(new View.OnClickListener() {
            final EditText etnCode = binding.etnCode;
            final EditText edcEmail = binding.edcEmail;
            final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            @Override
            public void onClick(View view) {
                String email = edcEmail.getText().toString().trim();
                String Code = etnCode.getText().toString().trim();
                if(email.isEmpty() && Code.isEmpty()){
                    Toast.makeText(getContext(), "llena los campos, Estan vacios", Toast.LENGTH_SHORT).show();
                }else{
                    if(email.isEmpty()){
                        Toast.makeText(getContext(), "Faltan campos, Ingresa tu correo", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(Code.isEmpty()){
                            Toast.makeText(getContext(), "Faltan campos, Ingresa tu correo", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(!email.isEmpty() && !Code.isEmpty()){
                                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                                final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                                httpClient.addInterceptor(logging);
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("https://eyiogthd.lucusvirtual.es/api/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(httpClient.build())
                                        .build();
                                validarApi val = retrofit.create(validarApi.class);
                                Call<validar> call = val.VALIDAR_CALL(email, Code);
                                call.enqueue(new Callback<validar>() {
                                    @Override
                                    public void onResponse(Call<validar> call, Response<validar> response) {
                                        if(response.isSuccessful() && response.body() != null){
                                            edcEmail.getText().clear();
                                            etnCode.getText().clear();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<validar> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
        return root;



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}