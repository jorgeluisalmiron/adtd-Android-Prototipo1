package com.apps.mobile.utn.adtd.geco.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.apps.mobile.utn.adtd.geco.Api.ApiClient;
import com.apps.mobile.utn.adtd.geco.Api.ApiInterface;
import com.apps.mobile.utn.adtd.geco.Api.LinkResponse;
import com.apps.mobile.utn.adtd.geco.Model.Link;
import com.apps.mobile.utn.adtd.geco.R;
import com.apps.mobile.utn.adtd.geco.Util.MyVolleySingleton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by Jorge Luis on 14/09/2016.
 */
public class LinksRecyclerViewAdapter extends RecyclerView.Adapter<LinksRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<Link> linkList=null;
    private RequestQueue requestQueue;
    private String TOKEN;




    public LinksRecyclerViewAdapter(Context context, String TOKEN)
    {
        this.context = context;
        this.TOKEN = TOKEN;
    }

    public void generateLinkList(final Context context)
    {
        ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LinkResponse> mService = mApiService.getLinkList(this.TOKEN);
        Log.d("JLA", "TOKEN: " +  this.TOKEN);
        mService.enqueue(new Callback<LinkResponse>() {
            @Override
            public void onResponse(Call<LinkResponse> call, retrofit2.Response<LinkResponse> response) {

                int statusCode = response.code();
                if (statusCode==200) { //OK
                    Log.d("JLA", "CODE: " + response.headers());
                    Log.d("JLA", "CODE: " +  response.body().getResults().get(0).getTitle());


                    linkList = response.body().getResults();
                    notifyDataSetChanged();

                }
                else {
                    Toast.makeText(context,"Error al consultar. Consulte con el administrador", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LinkResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(context,"Error al consultar. Consulte con el administrador", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void setAdapterList(List<Link> list)
    {
        this.linkList=list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.getTitulo().setText(linkList.get(position).getTitle());
        holder.getDescripcion().setText(linkList.get(position).getDescription());


        ImageLoader mImageLoader;

        mImageLoader = MyVolleySingleton.getInstance(context).getImageLoader();
        holder.getImagen().setImageUrl(linkList.get(position).getImageUrl(), mImageLoader);
    }

    @Override
    public int getItemCount() {
        if (linkList == null) {
            return 0;
        } else {
            return linkList.size();

        }
    }



    public Link getItem(int position){
        return linkList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo;
        private TextView descripcion;
        private NetworkImageView   imagen;


        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.textoTituloLink);
            descripcion = (TextView) itemView.findViewById(R.id.textoDescripcionLink);
            imagen = (NetworkImageView) itemView.findViewById(R.id.thumbnail_link);
        }


        public TextView getTitulo() {
            return titulo;
        }

        public void setTitulo(TextView titulo) {
            this.titulo = titulo;
        }

        public TextView getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(TextView descripcion) {
            this.descripcion = descripcion;
        }



        public NetworkImageView getImagen() {
            return imagen;
        }

        public void setImagen(NetworkImageView imagen) {
            this.imagen = imagen;
        }
    }
}
