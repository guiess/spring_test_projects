package org.fepo_spring_app;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.fepo_spring_app.entity.Customer;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainSpringWindow extends AppCompatActivity {

    //HttpRequestTask requestTask = new HttpRequestTask();
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_spring_window);
    }

    public void onSetConnection(View v){
        System.out.println("onSetConnection start");
        EditText connectionStringText = (EditText) findViewById(R.id.editText);
        url = connectionStringText.getText().toString();
        //requestTask.setUrl(connectionStringText.getText().toString());
        System.out.println("url "+url);
    }

    public void onGetCustomer(View v){
        System.out.println("onGetCustomer start");
        new HttpRequestTask(url).execute();
    }



    private class HttpRequestTask extends AsyncTask<Void, Void, Customer>{

        private String url = "localhost:8080";

        public void setUrl(String url){
            this.url = url;
        }

        public HttpRequestTask(String url){
            this.url = url;
        }

        @Override
        protected Customer doInBackground(Void... params){
            try {
                System.out.println("doInBackground start");
                EditText idText = (EditText) findViewById(R.id.editText2);
                System.out.println("doInBackground idText = " + idText);
                String connectionString = "http://"+url + "/findCustomerById?id=" + idText.getText().toString();
                System.out.println("doInBackground connectionString = " + connectionString);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Customer result = restTemplate.getForObject(connectionString, Customer.class);
                System.out.println("doInBackground result = " + result);
                return result;
            }
            catch(Exception e){
                System.out.println("doInBackground e = " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Customer customer){

            TextView id = (TextView) findViewById(R.id.id_value);
            TextView content = (TextView) findViewById(R.id.content_value);
            if(customer == null){
                content.setText("not found");
                return;
            }
            id.setText(String.valueOf(customer.getId()));
            content.setText(customer.getLastName());
        }

    }
}
