package kr.ac.gachon.searchdogs.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.ac.gachon.searchdogs.R
import java.lang.ClassCastException

class BottomSheetFragment : BottomSheetDialogFragment {

    private var mListener: BottomSheetListener? = null

    constructor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bnv_check_type, container, false)
        val imgBtnYes = view.findViewById<ImageButton>(R.id.imgBtn_yes)
        val imgBtnNo = view.findViewById<ImageButton>(R.id.imgBtn_no)

        imgBtnYes.setOnClickListener { view ->
            mListener!!.onButtonClicked("imgBtnYes clicked")
            dismiss()
        }
        imgBtnNo.setOnClickListener { view ->
            mListener!!.onButtonClicked("imgBtnNo clicked")
            dismiss()
        }

        return view
    }

    interface BottomSheetListener {
        fun onButtonClicked(text: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mListener = context as BottomSheetListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement BottomSheetListener")
        }

    }

}