
# Updates

### ViewModel & Repository [05/01/2019]

- Added DataSource and Injection components

``` kotlin
object Injection {

    fun providerRepository():MuseumRepository{
        return MuseumRepository()//could be a singleton
    }
}
```

``` kotlin
interface MuseumDataSource {

    fun retrieveMuseums(callback: OperationCallback)

    fun cancel()
}
```

``` kotlin
class MuseumRepository:MuseumDataSource {

    private var call:Call<MuseumResponse>?=null

    override fun retrieveMuseums(callback: OperationCallback) {
        ...
    }

    override fun cancel() {
        ...
    }
}
```
- Added ViewModelFactory component

``` kotlin
class ViewModelFactory(private val repository:MuseumRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MuseumViewModel(repository) as T
    }
}
```

``` kotlin
class MuseumViewModel(private val repository: MuseumRepository):ViewModel() {
...
}
```

``` kotlin
class MuseumActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum)

        //viewmodel
        setUpViewModel()

        ...
    }

    private fun setUpViewModel(){
    
        viewModel =     ViewModelProviders.of(this,ViewModelFactory(Injection.providerRepository())).get(MuseumViewModel::class.java)
        
        ...
        
        }
}
```

### ViewModel [04/23/2019]

- Replace 'value' method with 'postValue' method when is related to ui callbacks

MuseumViewModel

``` kotlin
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

``` kotlin
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

``` kotlin
   private val _museums = MutableLiveData<List<Museum>>().apply { value = emptyList() }
    val museums: LiveData<List<Museum>>
    get() = _museums
```

for this

``` kotlin
   private val _museums = MutableLiveData<List<Museum>>().apply { value = emptyList() }
    val museums: LiveData<List<Museum>> = _museums
```

- Add validation when the list is empty

MuseumViewModel

``` kotlin
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
``` kotlin
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
