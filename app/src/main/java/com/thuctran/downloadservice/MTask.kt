@file:Suppress("DEPRECATION")

package com.thuctran.downloadservice

import android.os.AsyncTask

@Suppress("DEPRECATION")
class MTask(
    private var key: String, //khai báo key để có thể phân biệt giữa các AsyncTask khác nhau
    private var callBack: OnMainCallBack // khai bao callBack để thực thi, thay thế cho PT setCallBack ở Activity
) : AsyncTask<Any, Any, Any>() {

    //PT setup các giá trị ban đầu trên luồng riêng hoặc reset giao diện (tiền xử lý)
    @Deprecated("Deprecated in Java")
    override fun onPreExecute() {
        callBack.preExec(key)

    }

    //PT thực thi các tác vụ trên luồng riêng, muốn cập nhật giao diện gọi publishProgress -> chạy đến PT progressUpdata, return false sẽ chạy xuống PT onPostExecute
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Any?): Any? {
        return callBack.execTask(key, if (params == null) null else params[0], this)
    }

    // gọi đến PT này để cập nhật giao diện (chạy xuống PT onProgressUpdate)
    fun requestUI(data: Any){
        onProgressUpdate(data)
    }

    //pT sử dụng data để update giao diện
    @Deprecated("Deprecated in Java")
    override fun onProgressUpdate(vararg data: Any?) {
        callBack.upDateUI(key, data[0]!!)
    }

    //PT xử lý khi kết thúc luồng, mặc định doInBackground return true nên sẽ là xử lý khi chạy xong
    @Deprecated("Deprecated in Java")
    override fun onPostExecute(value: Any?) {
        callBack.completeTask(key, value!!)
    }

    //PT xử lý khi gặp interrupted
    @Deprecated("Deprecated in Java")
    override fun onCancelled() {
        callBack.cancalTask(key)
    }

    //PT chạy tuần tự
    fun start(data: Any?) {
        execute(data)
    }

    //PT chạy đồng thời
    fun startAsync(data: Any) {
        executeOnExecutor(THREAD_POOL_EXECUTOR, data)
    }

    fun stop(data: Boolean) {
        cancel(data)
    }



    interface OnMainCallBack {
        // PT dùng cho onPreExecute, PT dùng chung cho mọi AsyncTask nên phải dùng key để phân biệt
        fun preExec(key: String) {
            //do sothing (khai báo như thế này, PT sẽ là PT default"Mặc định", có thể được ghi đè hoặc không trong các class khác
        }

        //PT dùng cho doInBackground
        fun execTask(key: String, params: Any?, task: MTask): Any?

        //PT dùng cho onProgressUpdate
        fun upDateUI(key: String, data: Any) {
            //do sothing
        }

        //PT dùng cho onPostExecute
        fun completeTask(key: String, data: Any) {
            //do sothing
        }

        //PT dùng cho onCancelled
        fun cancalTask(key: String) {
            //do sothing
        }
    }

}