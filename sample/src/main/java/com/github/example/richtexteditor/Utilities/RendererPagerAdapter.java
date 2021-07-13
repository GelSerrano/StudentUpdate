package com.github.example.richtexteditor.Utilities;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.github.example.richtexteditor.HTMLRenderedFragment;
import com.github.example.richtexteditor.PreviewFragment;
import com.github.example.richtexteditor.SerializedFragment;

/**
 * Created by mkallingal on 6/12/2016.
 */
public class RendererPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    final String content;
    private String tabTitles[] = new String[] { "Rendered", "Serialized", "HTML" };
    private Context context;
    public RendererPagerAdapter(FragmentManager fm, Context context,String content) {
        super(fm);
        this.context = context;
        this.content= content;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PreviewFragment.newInstance(content);
        }else if(position==1){
            return SerializedFragment.newInstance(content);
        }else{
            return HTMLRenderedFragment.newInstance(content);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
