package com.invoke.pricechecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.invoke.pricechecker.api.APIInterface;
import com.invoke.pricechecker.api.RetrofitInstances;
import com.invoke.pricechecker.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Objects.requireNonNull(binding.barcode.getEditText()).setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                String barcode = Objects.requireNonNull(binding.barcode.getEditText()).getText().toString().trim();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Custbarcode", barcode);
                    RequestBody requestBody = RequestBody.create(String.valueOf(jsonObject), MediaType.parse("application/json; charset=utf-8"));
                    APIInterface apiInterface = RetrofitInstances.getRetrofit().create(APIInterface.class);
                    apiInterface.getData(requestBody).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            if (response.body() != null) {
                                String data = null;
                                try {
                                    data = response.body().string();
                                    JSONObject datJsonObject = new JSONObject(data);
                                    binding.productName.setText(datJsonObject.getString("stockname"));
                                    binding.productPrice.setText(datJsonObject.getString("stockdrp"));
                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                showToast(response.message());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            showToast(t.getLocalizedMessage());
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

            Log.e("onCreate:", " If Else");
            return false;
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}