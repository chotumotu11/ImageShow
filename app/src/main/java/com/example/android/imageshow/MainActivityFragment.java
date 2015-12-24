package com.example.android.imageshow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */

class ImageArray<T> extends ArrayAdapter<T> {

    private LayoutInflater inflater;
    private int mresource;
    private T item;
    private ImageView image;
    ImageArray(Context context,int resource,int textviewresourceid,List<T> objects)
    {
            super(context,resource,textviewresourceid,objects);
            mresource =resource;
            inflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){

        View view;

        if(convertView==null)
        {
            view =inflater.inflate(mresource,parent,false);
        }
        else
        {
            view=convertView;
        }




        image = (ImageView) view;
        String item1 = (String)getItem(position);
        new ImageDownloader().execute(item1);


        return image;

    }
    class ImageDownloader extends AsyncTask<String,Void,Bitmap>{

        public Bitmap doInBackground(String... params)
        {

            Bitmap bmp=null;
            try {
                URL url = new URL(params[0]);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            }catch(Exception w)
            {
                Log.v("URL Problem", "Image not available at the URL");
            }
            if(bmp==null)
            {
                Log.v("Image error","Image processing error");
            }
            else
            {
                return bmp;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            if(bmp==null)
            {
                Log.v("URL Error","Image error");
            }
            else
                image.setImageBitmap(bmp);


        }
    }


}


public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        String[] picArray={
        "https://www.cse.iitb.ac.in/~pawangc/nature1.jpg","https://upload.wikimedia.org/wikipedia/commons/4/4f/Matterhorn_Riffelsee_2005-06-11.jpg"};


        List<String> forcast = new ArrayList<String>(Arrays.asList(picArray));
        ImageArray<String> boo =new ImageArray<String>(getActivity(),R.layout.image_view,R.id.Image_id,forcast);
        ListView listview = (ListView)rootview.findViewById(R.id.listview_forecast);
        listview.setAdapter(boo);


        return rootview;
    }
}
