package com.joe.transactionsapp.business.domain.state


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joe.transactionsapp.util.cLogD
import java.lang.IndexOutOfBoundsException

class MessageStack: ArrayList<StateMessage>() {

    private val _stateMessage: MutableLiveData<StateMessage?> = MutableLiveData()

    val stateMessage: LiveData<StateMessage?>
        get() = _stateMessage

    fun isStackEmpty(): Boolean{
        return size == 0
    }

    override fun addAll(elements: Collection<StateMessage>): Boolean {
        for(element in elements){
            add(element)
        }
        return true // always return true. We don't care about result bool.
    }

    override fun add(element: StateMessage): Boolean {
        if(this.contains(element)){ // prevent duplicate errors added to stack
            return false
        }
        val transaction = super.add(element)
        if(this.size == 1){
            setStateMessage(stateMessage = element)
        }
        return transaction
    }

    override fun removeAt(index: Int): StateMessage {
        try{
            val transaction = super.removeAt(index)
            if(this.size > 0){
                setStateMessage(stateMessage = this[0])
            }
            else{
                "MessageStack stack is empty: ".cLogD()
                setStateMessage(null)
            }
            return transaction
        }catch (e: IndexOutOfBoundsException){
            setStateMessage(null)
            e.printStackTrace()
        }
        return StateMessage( // this does nothing
            Response(
                message = "does nothing",
                uiComponentType = UIComponentType.None,
                messageType = MessageType.None
            )
        )
    }

    private fun setStateMessage(stateMessage: StateMessage?){
        _stateMessage.value = stateMessage
    }
}


























