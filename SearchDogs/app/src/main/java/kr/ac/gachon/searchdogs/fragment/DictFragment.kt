package kr.ac.gachon.searchdogs.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_dict.*
import kr.ac.gachon.searchdogs.R
import kr.ac.gachon.searchdogs.adapter.DictAdapter
import kr.ac.gachon.searchdogs.data.DogDTO

/**
 * A simple [Fragment] subclass.
 */
class DictFragment : Fragment() {
    var db = FirebaseFirestore.getInstance()
    var dogList = arrayListOf<DogDTO>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dict, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDog()
    }

    fun getDog() {
        db.collection("DogBreeds").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var dogdto = document.toObject(DogDTO::class.java)
                    dogList.add(dogdto!!)
                }
                val dogAdapter = DictAdapter(this.requireContext(), dogList)
                listView_Dict.adapter = dogAdapter
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

}
