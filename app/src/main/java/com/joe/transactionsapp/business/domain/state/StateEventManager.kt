package com.joe.transactionsapp.business.domain.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.joe.transactionsapp.util.cLogD

/**
 * - Keeps track of active StateEvents in DataChannelManager
 * - Keeps track of whether the progress bar should show or not based on a boolean
 *      value in each StateEvent (shouldDisplayProgressBar)
 */
class StateEventManager {

    private val activeStateEvents: HashMap<String, StateEvent> = HashMap()

    private val _shouldDisplayProgressBar: MutableLiveData<Boolean> = MutableLiveData()

    val shouldDisplayProgressBar: LiveData<Boolean>
            get() = _shouldDisplayProgressBar

    fun getActiveJobNames(): MutableSet<String>{
        return activeStateEvents.keys
    }

    fun clearActiveStateEventCounter(){
        "Clear active state events".cLogD()
        //EspressoIdlingResource.clear()
        activeStateEvents.clear()
        syncNumActiveStateEvents()
    }

    fun addStateEvent(stateEvent: StateEvent){
        //EspressoIdlingResource.increment()
        activeStateEvents[stateEvent.eventName()] = stateEvent
        syncNumActiveStateEvents()
    }

    fun removeStateEvent(stateEvent: StateEvent?){
        "remove state event: ${stateEvent?.eventName()}".cLogD()
        stateEvent?.let {
            //EspressoIdlingResource.decrement()
        }
        activeStateEvents.remove(stateEvent?.eventName())
        syncNumActiveStateEvents()
    }

    fun isStateEventActive(stateEvent: StateEvent): Boolean{
        "${stateEvent.eventName()} is state event active? " +
                "${activeStateEvents.containsKey(stateEvent.eventName())}".cLogD()
        return activeStateEvents.containsKey(stateEvent.eventName())
    }

    private fun syncNumActiveStateEvents(){
        var shouldDisplayProgressBar = false
        for(stateEvent in activeStateEvents.values){
            if(stateEvent.shouldDisplayProgressBar()){
                shouldDisplayProgressBar = true
            }
        }
        _shouldDisplayProgressBar.value = shouldDisplayProgressBar
    }
}

















