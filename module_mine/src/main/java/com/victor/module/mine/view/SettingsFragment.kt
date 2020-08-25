package com.victor.module.mine.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.victor.lib.common.base.BaseFragment
import com.victor.module.mine.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SettingsFragment
 * Author: Victor
 * Date: 2020/8/24 下午 05:53
 * Description: 
 * -----------------------------------------------------------------
 */

class SettingsFragment: PreferenceFragmentCompat() {
    companion object {
        fun newInstance(): SettingsFragment {
            val fragment = SettingsFragment()
            return fragment
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}