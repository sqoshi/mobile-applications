# mobile-applications
# Table Of Contents
- [Roshambo](#roshambo)
	- [Layout](#layout)
		- [Portrait](#portrait)
		- [Landscape](#landscape)
		
---------------------------
# [Roshambo](https://github.com/sqoshi/mobile-applications/list01/exercise2)

[Rock paper scissors](https://en.wikipedia.org/wiki/Rock_paper_scissors).
Simple game implementation in Kotlin.

### Layout
#### Portrait
![](list01/media/s1.png)

#### Landscape
![](list01/media/s2.png)
### Code Example
```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState != null) {
            val value = savedInstanceState.getInt("counter")
            binding.counter.text = value.toString()
            counter = value
        }
    }
```
---------------------------
