package com.pedif.nyhighschoolinfo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.pedif.nyhighschoolinfo.R
import com.pedif.nyhighschoolinfo.data.repository.Repository
import com.pedif.nyhighschoolinfo.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment to show a list of all available high schools
 */
@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var repository: Repository
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        //bind the custom toolbar
        with(binding.toolBar) {
            title = resources.getString(R.string.app_name)
            (activity as AppCompatActivity?)!!.setSupportActionBar(this)
        }

        initList()
        return binding.root
    }

    private fun initList() {

        binding.rvSchool.apply {
            addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                ).also {
                    it.setDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.list_divider_line
                        )!!
                    )
                }

            )
            val clickListener = SchoolAdapter.OnclickListener { school ->
                val action =
                    MainFragmentDirections.actionMainFragmentToHighSchoolFragment(dbn = school.dbn, title = school.school_name)
                findNavController().navigate(action)
            }
            adapter = SchoolAdapter(clickListener)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
    }

    private fun initObservers() {

        viewModel.schools.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.pbLoading.isVisible = false
                (binding.rvSchool.adapter as SchoolAdapter).items = it
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Snackbar.make(binding.rvSchool, it, Snackbar.LENGTH_LONG).show()
                viewModel.errorDisplayed()
            }
        }
    }

}