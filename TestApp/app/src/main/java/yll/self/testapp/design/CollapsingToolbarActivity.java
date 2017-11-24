package yll.self.testapp.design;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yll.self.testapp.R;
import yll.self.testapp.utils.RecyclerItemClickListener;
import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 2016/3/11.
 * CollapsingToolbarLayout包裹 Toolbar 的时候提供一个可折叠的 Toolbar，一般作为AppbarLayout的子视图使用。
 * CollapsingToolbarLayout 提供以下属性和方法是用：

 Collapsing title：ToolBar的标题，当CollapsingToolbarLayout全屏没有折叠时，title显示的是大字体，在折叠的过程中，
 title不断变小到一定大小的效果。你可以调用setTitle(CharSequence)方法设置title。
 Content scrim：ToolBar被折叠到顶部固定时候的背景，你可以调用setContentScrim(Drawable)方法改变背景或者
 在属性中使用 app:contentScrim=”?attr/colorPrimary”来改变背景。
 Status bar scrim：状态栏的背景，调用方法setStatusBarScrim(Drawable)。还没研究明白，不过这个只能在Android5.0以上系统有效果。
 Parallax scrolling children：CollapsingToolbarLayout滑动时，子视图的视觉差，
 可以通过属性app:layout_collapseParallaxMultiplier=”0.6”改变。
 CollapseMode ：子视图的折叠模式，有两种“pin”：固定模式，在折叠的时候最后固定在顶端；
            “parallax”：视差模式，在折叠的时候会有个视差折叠的效果。我们可以在布局中使用属性app:layout_collapseMode=”parallax”来改变。
 */
public class CollapsingToolbarActivity extends Activity {


    private CollapsingToolbarLayout collapsing_toolbar;

    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager vp_viewpager;
    private FloatingActionButton fab;
    private AppBarLayout appbar;


    private boolean isExpand = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaping);
//        recycler = (RecyclerView) findViewById(R.id.recycler);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (TabLayout) findViewById(R.id.tabs);
        vp_viewpager = (ViewPager) findViewById(R.id.vp_viewpager);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpand) {
                    appbar.setExpanded(false, false);
                }else {
                    appbar.setExpanded(true, false);
                }
                isExpand = !isExpand;
            }
        });
        initToolBar();
        initCollapsing();
        initTab();
        initViewPager();
    }



    private void initTab(){
//        List<String> tabList = new ArrayList<>();
//        tabList.add("Tab1");
//        tabList.add("Tab2");
//
//        for (int i = 0; i < tabList.size(); i++) {
//            if (i ==0){
//                tabs.addTab(tabs.newTab().setText(tabList.get(i)), true);
//            }else {
//                tabs.addTab(tabs.newTab().setText(tabList.get(i)), false);
//            }
//        }
        tabs.setupWithViewPager(vp_viewpager);
        tabs.setSelectedTabIndicatorHeight(UtilsManager.dip2px(this, 2));
    }


    private void initToolBar(){
//        setSupportActionBar(toolbar);
//        setTitle("Test");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_back_light));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CollapsingToolbarActivity.this, "icon被点击", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void initCollapsing(){
//        collapsing_toolbar.setTitle("挑一个男票");
//        /**展开时文字颜色*/
//        collapsing_toolbar.setExpandedTitleColor(Color.RED);
//        /**折叠时文字颜色*/
//        collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE);
//        /**折叠时背景色*/
//        collapsing_toolbar.setContentScrimColor(Color.BLUE);
    }

    private void initViewPager(){

        ArrayList<View> pageViews = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        CollapsingView view = new CollapsingView(this);
        CollapsingView view1 = new CollapsingView(this);

        pageViews.add(view.getRoot());
        pageViews.add(view1.getRoot());

        titles.add("热门");
        titles.add("最新");

        MyPagerAdapter adapter = new MyPagerAdapter(pageViews, titles);
        vp_viewpager.setAdapter(adapter);
    }

    private class MyPagerAdapter extends PagerAdapter{
        private ArrayList<View> pageViews = new ArrayList<>();
        private ArrayList<String> titles = new ArrayList<>();

        public MyPagerAdapter(ArrayList<View> pageViews, ArrayList<String> titles){
            this.pageViews = pageViews;
            this.titles = titles;
        }

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                container.addView(pageViews.get(position));
            } catch (Exception e) {
                container.removeView(pageViews.get(position));
                container.addView(pageViews.get(position));
                e.printStackTrace();
            }
            return pageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pageViews.get(position));
        }
    }


}
