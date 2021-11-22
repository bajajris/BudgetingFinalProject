package project.st991532818.org.ui.savings

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.st991532818.org.R
import project.st991532818.org.databinding.FragmentAddactivityBinding
import project.st991532818.org.databinding.FragmentSavingsBinding
import org.eazegraph.lib.models.PieModel




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavingsFragment : Fragment() {
    private var _binding: FragmentSavingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSavingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tvR.text = Integer.toString(25)
        binding.tvCPP.text = Integer.toString(25)
        binding.tvJava.text = Integer.toString(25)
        binding.tvPython.text = Integer.toString(25)

        binding.piechart.addPieSlice(
            PieModel(
                "R", binding.tvR.getText().toString().toFloat(),
                Color.parseColor("#FFA726")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "R", binding.tvPython.getText().toString().toFloat(),
                Color.parseColor("#66BB6A")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "R", binding.tvCPP.getText().toString().toFloat(),
                Color.parseColor("#EF5350")
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                "R", binding.tvJava.getText().toString().toFloat(),
                Color.parseColor("#29B6F6")
            )
        )

        // Inflate the layout for this fragment
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}