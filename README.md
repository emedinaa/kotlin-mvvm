# kotlin-mvvm
MVVM(Model View ViewModel) sample in Kotlin using the components ViewModel, LiveData and Retrofit library

## MVVM Pattern (Model View ViewModel)
 <img src="AndroidMVVM.png">
 
## Flow
<img src="MVVMFlow.png">
 
## Demo

<img src="./kotlinmvvmscreenrotation720.gif?raw=true" height="480">

## Screenshots

<img src="./screenshot.png" height="480"> <img src="./screenshot_error.png" height="480"> <img src="./kotlinmvvm720.gif?raw=true" height="480"> <img src="./screenshot_empty.png" height="480">

## Update [04/23/2019]

- Replace 'value' method to 'postValue' method when is related to ui callbacks

MuseumViewModel

```
  fun loadMuseums(){
        _isViewLoading.value=true
        repository.retrieveMuseums(object:OperationCallback{
            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.value= obj
            }
  ...          
```

for this

```
 fun loadMuseums(){
        _isViewLoading.postValue(true)
        repository.retrieveMuseums(object:OperationCallback{
            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue( obj)
            }

            override fun onSuccess(obj: Any?) {
                _isViewLoading.postValue(false)

                if(obj is List<*>){
                    if(obj.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _museums.value= obj as List<Museum>
                    }
                }
            }
        })
```

- Remove 'get' method

MuseumViewModel

```
   private val _museums = MutableLiveData<List<Museum>>().apply { value = emptyList() }
    val museums: LiveData<List<Museum>>
    get() = _museums
```

for this

```
   private val _museums = MutableLiveData<List<Museum>>().apply { value = emptyList() }
    val museums: LiveData<List<Museum>> = _museums
```

- Add validation when the list is empty

MuseumViewModel

```
  private val _isEmptyList=MutableLiveData<Boolean>()
  val isEmptyList:LiveData<Boolean> = _isEmptyList
  
  ...
  
   fun loadMuseums(){
        _isViewLoading.value=true
        repository.retrieveMuseums(object:OperationCallback{
            override fun onError(obj: Any?) {
                _isViewLoading.postValue(false)
                _onMessageError.value= obj
            }

            override fun onSuccess(obj: Any?) {
                if(obj is List<*>){
                    _isViewLoading.postValue(false)
                    if(obj.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _museums.value= obj as List<Museum>
                    }
                }
            }
        })
    }
```

MuseumActivity
```
    private fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this).get(MuseumViewModel::class.java)
        ...
        
        viewModel.isEmptyList.observe(this,emptyListObserver)
        
        ...
        
        private val emptyListObserver= Observer<Boolean> {
         Log.v(TAG, "emptyListObserver $it")
         layoutEmpty.visibility=View.VISIBLE
         layoutError.visibility=View.GONE
        }
    }
```

## Dependencies

- Retrofit https://square.github.io/retrofit/
- OkHttp https://square.github.io/okhttp/
- Glide https://github.com/bumptech/glide

## References

- ViewModel https://developer.android.com/topic/libraries/architecture/viewmodel
- LiveData https://developer.android.com/topic/libraries/architecture/livedata
- Android Architecture Blueprints https://github.com/googlesamples/android-architecture
- Android Jetpack: ViewModel https://www.youtube.com/watch?v=5qlIPTDE274

I appreciate any question or comment and could you give me a star if you consider useful. Also, you can put any issue at https://github.com/emedinaa/kotlin-mvvm/issues
