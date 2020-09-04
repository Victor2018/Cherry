package com.victor.module.mine.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.victor.module.mine.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: EditUserFragment
 * Author: Victor
 * Date: 2020/8/24 下午 05:53
 * Description: 
 * -----------------------------------------------------------------
 */

class EditUserFragment: PreferenceFragmentCompat() {
    companion object {
        fun newInstance(): EditUserFragment {
            val fragment = EditUserFragment()
            return fragment
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.edit_user_preferences, rootKey)
    }
}