package me.dmjohnson.wgu.c196.ui;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import me.dmjohnson.wgu.c196.AssessmentListFragment;
import me.dmjohnson.wgu.c196.CourseDetailsFragment;
import me.dmjohnson.wgu.c196.CourseNotesFragment;
import me.dmjohnson.wgu.c196.R;
import me.dmjohnson.wgu.c196.ui.main.PlaceholderFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class CoursePagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_course_info, R.string.tab_course_mentor, R.string.tab_course_assessments, R.string.tab_course_notes};
    private final Context context;
    private final Integer courseId;

    public CoursePagerAdapter(Context context, FragmentManager fm, Integer courseId) {
        super(fm);
        this.courseId = courseId;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                // Course Info Tab
                return CourseDetailsFragment.newInstance(courseId);
            case 1:
                // Course Mentor Tab
                return PlaceholderFragment.newInstance(2);
            case 2:
                // AssessmentsTab
                return AssessmentListFragment.newInstance(courseId);
            case 3:
                // Notes tab
                return CourseNotesFragment.newInstance();
        }
        throw new RuntimeException("Undefined tab");
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 4;
    }
}