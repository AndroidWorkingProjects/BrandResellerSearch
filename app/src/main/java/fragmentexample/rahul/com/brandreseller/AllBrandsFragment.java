package fragmentexample.rahul.com.brandreseller;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllBrandsFragment extends Fragment implements VolleyJsonGetRequest.VolleyJsonGetResponse{

    private String getBrandsUrl = "";
    private String getBrandsResponse = "";
    private VolleyJsonGetRequest volleyRequest;
    private String userType;
    private RecyclerView recyclerView;
    private List<Item> itemList;
    private MyRecyclerAdapter adapter;

    String jsonFile = "{\"brands\": [\"Brand 1\",\"Brand 2\",\"Brand 3\",\"Brand 4\",\"Brand 5\",\"Brand 6\",\"Brand 7\",\"Brand 8\",\"Brand 9\",\"Brand 10\",\"Brand 11\",\"Brand 12\"]}";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("AllBrandsFragment", "onCreate()");
        itemList = new ArrayList<Item>();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getBrandsData() {

        /*
        volleyRequest = new VolleyJsonGetRequest(MainActivity.this, url);
                volleyRequest.submitGetRequest();
         */

        try {
            JSONObject brandsData = new JSONObject(jsonFile);

            JSONArray jsonArray = brandsData.getJSONArray("brands");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    String brand = jsonArray.getString(i);
                    itemList.add(new Item(brand, null, null,Item.Type.BRAND));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("DetailFragment", "onCreateView()");
        View view = inflater.inflate(R.layout.allbrands, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.allbrandslistview);
        userType = ((MainActivity) getActivity()).userType;
        getBrandsData();

        adapter = new MyRecyclerAdapter(getActivity(), itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void getResponse(String response, int status) {
        if( status == 200){
            getBrandsResponse = response;

            /* Implement parsing here */
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected ImageView icon;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            view.setClickable(true);
            view.setOnClickListener(this);
            this.textView = (TextView) view.findViewById(R.id.brandtext);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),"onClick "+getLayoutPosition(),Toast.LENGTH_SHORT).show();
        }
    }

    public class MyRecyclerAdapter extends RecyclerView.Adapter<CustomViewHolder> {
        private List<Item> itemList;

        public MyRecyclerAdapter(Context context, List<Item> itemList) {
            this.itemList = itemList;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.allbrandsitem, null);

            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
            Item brandItem = itemList.get(i);

            customViewHolder.textView.setText(brandItem.getItemName());
        }

        @Override
        public int getItemCount() {
            return (null != itemList ? itemList.size() : 0);
        }
    }
}
