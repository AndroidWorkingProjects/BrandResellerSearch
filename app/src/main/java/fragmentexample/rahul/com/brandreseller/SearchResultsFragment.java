package fragmentexample.rahul.com.brandreseller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsFragment  extends Fragment {

    String brandsAndProduct = "{\"brands\": [{\"bname\":\"drinks\"},{\"bname\":\"chips\"},{\"bname\":\"snacks\"}],\n" +
            " \"products\": [{\"pname\":\"pepsi\"},{\"pname\":\"lays\"},{\"pname\":\"noodles\"}]}";
    String noItems = "{\"brands\": [],\n" +
            "  \"products\": []}";

    ArrayList<Item> itemsList;

    private RecyclerView recyclerView;
    private SearchRecyclerAdapter searchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("Search Results", "onCreateView()");
        View view = inflater.inflate(R.layout.searchresults, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.searchlistview);

        itemsList = new ArrayList<Item>();

        recyclerView = (RecyclerView) view.findViewById(R.id.searchlistview);

        searchAdapter = new SearchRecyclerAdapter(getActivity(), itemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(searchAdapter);

        return view;
    }

    public void doSearch(String searchQuery){

        //
        // Here, we need to implement the rest call to get the search results and call parseData() to parse and fill the itemsList structure
        //

        itemsList.clear();
        Item newItem = new Item(searchQuery,null,null,Item.Type.NEW);
        itemsList.add(newItem);
        parseData();
        searchAdapter.notifyDataSetChanged();
    }

    private void parseData() {
        try {
            JSONObject itemsData = new JSONObject(brandsAndProduct);

            JSONArray jsonArrayBrands = itemsData.getJSONArray("brands");
            JSONArray jsonArrayProducts = itemsData.getJSONArray("products");

            if(jsonArrayBrands.length()>0){
                for (int i = 0; i < jsonArrayBrands.length(); i++) {
                    JSONObject brand = jsonArrayBrands.getJSONObject(i);
                    itemsList.add(new Item(brand.getString("bname"), null, null,Item.Type.BRAND));
                }
            }
            if (jsonArrayProducts.length() > 0) {
                for (int i = 0; i < jsonArrayProducts.length(); i++) {
                    JSONObject product = jsonArrayProducts.getJSONObject(i);
                    itemsList.add(new Item(product.getString("pname"), null, null,Item.Type.PRODUCT));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class BrandViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView textView;

        public BrandViewHolder(View view) {
            super(view);
            view.setClickable(true);
            view.setOnClickListener(this);
            this.textView = (TextView) view.findViewById(R.id.listbrand);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Brand onClick " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView textView;

        public ProductViewHolder(View view) {
            super(view);
            view.setClickable(true);
            view.setOnClickListener(this);
            this.textView = (TextView) view.findViewById(R.id.listproduct);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Product onClick " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        }
    }
    public class NewItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView textView;

        public NewItemViewHolder(View view) {
            super(view);
            view.setClickable(true);
            view.setOnClickListener(this);
            this.textView = (TextView) view.findViewById(R.id.newitem);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "New onClick " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        }
    }

    public class SearchRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Item> itemList;

        public SearchRecyclerAdapter(Context context, List<Item> itemList) {
            this.itemList = itemList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            if(i==1) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand, null);
                return new BrandViewHolder(view);
            }
            else if(i==2){
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product, null);
                return new ProductViewHolder(view);
            }
            else{
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.createitem, null);
                return new NewItemViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder customViewHolder, int i) {
            Item item = itemList.get(i);

            Log.i("Types"," "+i+"  "+item.getItemType());
            if(item.getItemType() == Item.Type.BRAND) {
                BrandViewHolder viewHolder = (BrandViewHolder)customViewHolder;
                viewHolder.textView.setText(item.getItemName() +" (Brand)");
            }
            else if(item.getItemType() == Item.Type.PRODUCT){
                ProductViewHolder viewHolder = (ProductViewHolder)customViewHolder;
                viewHolder.textView.setText(item.getItemName()+" (Product)");
            }
            else{
                NewItemViewHolder viewHolder = (NewItemViewHolder)customViewHolder;
                viewHolder.textView.setText(item.getItemName()+" (New)");            }
        }

        @Override
        public int getItemViewType(int position) {
            if(itemsList.get(position).getItemType() == Item.Type.BRAND){
                return 1;
            }
            else if(itemsList.get(position).getItemType() == Item.Type.PRODUCT){
                return 2;
            }
            else{
                return 3;
            }
        }

        @Override
        public int getItemCount() {
            return (null != itemList ? itemList.size() : 0);
        }
    }
}
