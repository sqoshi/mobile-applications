# mobile-applications
# Table Of Contents
- [mobile-applications](#mobile-applications)
- [Table Of Contents](#table-of-contents)
- [Roshambo](#roshambo)
    - [Layout](#layout)
      - [Portrait](#portrait)
      - [Landscape](#landscape)
    - [Code Example](#code-example)
- [TicTacToe](#tictactoe)
    - [Layout](#layout-1)
      - [Main](#main)
      - [Board3x3](#board3x3)
      - [Round Win](#round-win)
    - [Code Example](#code-example-1)
- [HangMan](#hangman)
    - [Layout](#layout-2)
      - [Portrait](#portrait-1)
      - [Landscape](#landscape-1)
      - [Lost Popup](#lost-popup)
    - [Code Example](#code-example-2)
  - [ToDo](#todo)
    - [Introduction](#introduction)
    - [General Info](#general-info)
    - [Layout](#layout-3)
      - [Portrait](#portrait-2)
        - [Tasks list](#tasks-list)
        - [Task update](#task-update)
        - [Task deletion](#task-deletion)
        - [All tasks deletion](#all-tasks-deletion)
      - [Landscape](#landscape-2)
        - [Tasks list](#tasks-list-1)
        - [Task addition](#task-addition)
    - [Code Example](#code-example-3)




---------------------------
# [Roshambo](https://github.com/sqoshi/mobile-applications/tree/master/list01/exercise2)

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

# [TicTacToe](https://github.com/sqoshi/mobile-applications/tree/master/list02/TicTacToe)

[TicTacToe](https://en.wikipedia.org/wiki/Tic-tac-toe) game implementation in Kotlin.

Games offers 2 boards in size 3x3 and 5x5.

Players can play kotlin.maxint rounds and still recognize who is winning.

### Layout
#### Main
![](list02/TicTacToe/media/front.png)

#### Board3x3
![](list02/TicTacToe/media/3x3.png)

#### Round Win
Example win in 5x5 board mode.

![](list02/TicTacToe/media/5x5r.png)

### Code Example
```kotlin
    private fun onButtonClick(button: Button, r: Int, c: Int) {
        if (!isFieldBusy(button)) {
            if (player1Turn) {
                button.text = player1Symbol
                button.setBackgroundColor(Color.parseColor("#5e60ce"))
                player1Turn = false
                player1Fields.add(intArrayOf(r, c))
                if (hasWin(player1Fields)) {
                    basicAlert(findViewById(R.id.resetButton), "Player1 has won")
                    player1Score += 1
                    updateScore(player1ScoreTextView, player1Score)

                }

            } else {
                button.text = player2Symbol
                button.setBackgroundColor(Color.parseColor("#64dfdf"))
                player1Turn = true
                player2Fields.add(intArrayOf(r, c))
                if (hasWin(player2Fields)) {
                    basicAlert(findViewById(R.id.resetButton), "Player2 has won")
                    player2Score += 1
                    updateScore(player2ScoreTextView, player2Score)
                }

            }

            if ((player1Fields.size + player2Fields.size).toDouble() == size.toDouble().pow(2.0)) {
                basicAlert(findViewById(R.id.resetButton), "Draw")
                roundCounter++
            }
        } else {
            Toast.makeText(applicationContext, "Field is busy.", Toast.LENGTH_SHORT).show()

        }
    }
```

----------------------------
# [HangMan](https://github.com/sqoshi/mobile-applications/tree/master/list02/TicTacToe)
[Hangman](https://en.wikipedia.org/wiki/Hangman_(game)) game implementation in Kotlin.

### Layout
#### Portrait
![](list02/HangMan/media/lyt.png)

#### Landscape
![](list02/HangMan/media/lytland.png)

#### Lost Popup

![](list02/HangMan/media/rs.png)

### Code Example
```kotlin
    private fun onOrientationChange(savedInstanceState: Bundle) {
        imageIndex = savedInstanceState.getInt("imageIndex")
        displayNextHangmanImage(imageIndex)
        currentWord = savedInstanceState.getString("currentWord").toString()
        val alreadyDiscoveredLettersAsStr = savedInstanceState.getString("alreadyDiscoveredLetters")
        if (alreadyDiscoveredLettersAsStr != null) {
            alreadyDiscoveredLetters = alreadyDiscoveredLettersAsStr.split(" ").toHashSet()
        }
        showBlurredWord(currentWord)
        for (x in alreadyDiscoveredLetters) {
            showLetter(x)
        }

    }
```
----------------------------
## [ToDo](https://github.com/sqoshi/mobile-applications/tree/master/list03/ToDo)

### Introduction
Simple implementation of task list in kotlin. This app may be usefull for android users that sometimes forgot about their schedule. 

### General Info
Application allow to add tasks to mutable list which is displayed as main fragment of program. After task addition program adds `alarm` by alarm manager to our phone. When task is close to expiration date phone vibrates, ringing and user face clickable popup that leads to `ToDo` app activity or creates new one.

User can perform operations as `read`,`add`,`delete`,`update`, `delete-all` and `sort` by every field in task table so we can say that app handles CRUD requests.

Sorts:
![](list03/ToDo/media/sorts.png)

Application stores every task in `Room` abstract database. 
Task structure: 
```kotlin
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val date: Date,
    val description: String,
    val type: String,
    val priority: String,
) : Parcelable
```

Application handles saves instances values in outState and remembers thats how remeber configuration state after rotation.

User can set priority and type of the task.

Priority is an `Enum(HIGH,MEDIUM,LOW)` where the priority correspond to the appropriate color of task.

Type is responsible for an icon app allows for types as on the screen below:
![](list03/ToDo/media/icon_chooser.png)

### Layout
Application handles both landscape and portrait layouts.

#### Portrait
##### Tasks list
![](list03/ToDo/media/list.png)

##### Task update
![](list03/ToDo/media/update.png)
##### Task deletion
![](list03/ToDo/media/rm2.png)
##### All tasks deletion
![](list03/ToDo/media/rm1.png)
#### Landscape
##### Tasks list
![](list03/ToDo/media/list_land.png)
##### Task addition
![](list03/ToDo/media/add_land.png)

### Code Example

``` kotlin
private fun setUpNotification(hour: Int, minute: Int, day: Int, month: Int, year: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        if (calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 1)
        val intent = Intent(activity?.applicationContext, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            activity?.applicationContext,
            (0..2147483647).random(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager =
            activity?.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
    ```

----------------------------
