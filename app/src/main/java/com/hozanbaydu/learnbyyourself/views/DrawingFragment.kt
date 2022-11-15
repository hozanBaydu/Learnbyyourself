package com.hozanbaydu.learnbyyourself.views


import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hozanbaydu.learnbyyourself.R
import com.hozanbaydu.learnbyyourself.databinding.FragmentDrawingBinding
import com.hozanbaydu.learnbyyourself.util.Status
import com.hozanbaydu.learnbyyourself.viewmodel.WordViewModel
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.OutputStream


class DrawingFragment:Fragment(R.layout.fragment_drawing){

    private var fragmentBinding : FragmentDrawingBinding? = null
    lateinit var wordViewModel: WordViewModel
    private lateinit var smallBitmap: Bitmap


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        Toast.makeText(requireContext(),"Lütfen resim çizdikten sonra yukarıdaki butondan kaydettikten sonra kelimeyi kaydedin",Toast.LENGTH_LONG).show()



        wordViewModel = ViewModelProvider(requireActivity()).get(WordViewModel::class.java)

        val binding = FragmentDrawingBinding.bind(view)
        fragmentBinding = binding

        val buttonAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim)
        binding.save.startAnimation(buttonAnimation)

        binding.drawingView.setBrushColor(Color.BLUE)

        binding.back.setOnClickListener {
            binding.drawingView.undo()
        }

        binding.further.setOnClickListener {
            binding.drawingView.redo()
        }

        binding.palet.setOnClickListener {
            binding.drawingView.setBrushColor(Color.RED)
        }

        binding.bluePalet.setOnClickListener {
            binding.drawingView.setBrushColor(Color.BLUE)
        }

        binding.delete.setOnClickListener {
            binding.drawingView.clearDrawingBoard()
        }

        binding.pencil.setOnClickListener {
            binding.drawingView.setSizeForBrush(20)

        }

        binding.smallPencil.setOnClickListener {
            binding.drawingView.setSizeForBrush(10)
        }

        binding.save.setOnClickListener {

            var bitmap = getBitmapFromView(binding.drawingLayout)


            if (bitmap != null) {
                smallBitmap=makeSmallBitmap(bitmap,200)
            }

            smallBitmap.saveImage(context = requireContext())

            val imageUri = smallBitmap?.saveImage(requireContext()).toString()
            println(imageUri)


            wordViewModel.setSelectedImage(imageUri)


        }

        binding.kaydet.setOnClickListener {
            wordViewModel.makeWord(binding.drawingNameText.text.toString(),binding.drawingSentenceText.text.toString())



            wordViewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
                when(it.status){
                    Status.SUCCESS ->{
                        Toast.makeText(requireContext(),"sucses", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        wordViewModel.resetInsertArtMsg()
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(),it.message ?:"error", Toast.LENGTH_SHORT).show()

                    }
                    Status.LOADING ->{}
                }
            })
        }
    }



    private fun makeSmallBitmap(image:Bitmap,maximumSize:Int): Bitmap {
        var width=image.width
        var height=image.height

        var bitmapRatio : Double=width.toDouble()/height.toDouble()

        if (bitmapRatio > 1){

            width=maximumSize
            val scaledHeight=width/bitmapRatio
            height=scaledHeight.toInt()
        }else{

            height=maximumSize
            val scaledWidth=height*bitmapRatio
            width=scaledWidth.toInt()
        }
        return Bitmap.createScaledBitmap(image,width,height,true)

    }



    private fun getBitmapFromView(view: View): Bitmap? {
        view.isDrawingCacheEnabled = true
        return view.drawingCache
    }

    fun Bitmap.saveImage(context: Context): Uri? {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/test_pictures")
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "img_${SystemClock.uptimeMillis()}")

            val uri: Uri? =
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                saveImageToStream(this, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
                return uri
            }
        } else {
            val directory =
                File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + separator + "test_pictures")
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName =  "img_${SystemClock.uptimeMillis()}"+ ".jpeg"
            val file = File(directory, fileName)
            saveImageToStream(this, FileOutputStream(file))
            if (file.absolutePath != null) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                // .DATA is deprecated in API 29
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                return Uri.fromFile(file)
            }
        }
        return null
    }


    fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}