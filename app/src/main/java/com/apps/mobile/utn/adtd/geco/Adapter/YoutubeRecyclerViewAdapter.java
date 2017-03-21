package com.apps.mobile.utn.adtd.geco.Adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.apps.mobile.utn.adtd.geco.Api.ApiClient;
import com.apps.mobile.utn.adtd.geco.Api.ApiInterface;
import com.apps.mobile.utn.adtd.geco.Api.YoutubeResponse;
import com.apps.mobile.utn.adtd.geco.Model.Youtube;
import com.apps.mobile.utn.adtd.geco.R;

import com.squareup.picasso.Picasso;



import java.net.MalformedURLException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Jorge Luis on 14/09/2016.
 */
public class YoutubeRecyclerViewAdapter extends RecyclerView.Adapter<YoutubeRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<Youtube> YoutubeLinksList =null;
    private String TOKEN;


    public YoutubeRecyclerViewAdapter(Context context, String TOKEN)

    {
        this.context = context;
        this.TOKEN = TOKEN;
    }

    public void generateYoutubeList(final Context context)
    {
        ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);
        Call<YoutubeResponse> mService = mApiService.getYoutubeList(this.TOKEN);
        Log.d("JLA", "TOKEN: " +  this.TOKEN);
        mService.enqueue(new Callback<YoutubeResponse>() {
            @Override
            public void onResponse(Call<YoutubeResponse> call, retrofit2.Response<YoutubeResponse> response) {



                int statusCode = response.code();
                if (statusCode==200) { //OK
                    Log.d("JLA", "CODE: " + response.headers());
                    Log.d("JLA", "CODE: " +  response.body().getResults().get(0).getTitle());


                    YoutubeLinksList = response.body().getResults();
                    notifyDataSetChanged();

                }
                else {
                    Toast.makeText(context,"Error al consultar. Consulte con el administrador", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<YoutubeResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(context,"Error al consultar. Consulte con el administrador", Toast.LENGTH_LONG).show();
            }
        });
    }

    public Youtube getItem(int position)
    {
        return YoutubeLinksList.get(position);

    }
    public void setAdapterList(List<Youtube> YoutubeLinksList )
    {
        this.YoutubeLinksList =YoutubeLinksList ;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            holder.getTitulo().setText(YoutubeLinksList.get(position).getTitle());
            holder.getDescripcion().setText(YoutubeLinksList.get(position).getDescription());
            String videoId = extractYoutubeId(YoutubeLinksList.get(position).getUrl().toString());

            Log.e("VideoId is->", "" + videoId);

            String img_url = "http://img.youtube.com/vi/" + videoId + "/0.jpg"; // this is link which will give u thumnail image of that video


            Picasso.with(context)
                    .load(img_url)
                    .placeholder(R.drawable.ic_add_circle_outline_black_24dp)
                    .into(holder.getImage());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (YoutubeLinksList == null) {
            return 0;
        } else {
            return YoutubeLinksList.size();

        }
    }



    public String extractYoutubeId(String url) throws MalformedURLException {
   /*     String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;*/
        String video_id="";
        if (url != null && url.trim().length() > 0 && url.startsWith("http"))
        {

            String expression = "^.*((youtu.be"+ "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
            CharSequence input = url;
            Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches())
            {
                String groupIndex1 = matcher.group(7);
                if(groupIndex1!=null && groupIndex1.length()==11)
                    video_id = groupIndex1;
            }
        }
        return video_id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo;
        private TextView descripcion;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.textoTituloYoutube);
            descripcion = (TextView) itemView.findViewById(R.id.textoDescripcionYoutube);
            image = (ImageView) itemView.findViewById(R.id.thumnail_youtube);
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

        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }
    }

}
