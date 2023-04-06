package baldufal.goldesel.ui.analysis

import java.time.LocalDateTime

enum class TimeFrame(val pretty: String) {
    TODAY("Today"){
        override fun calculateStartDateTime(): LocalDateTime {
            var time = LocalDateTime.now()
            time = time.withHour(0)
            time = time.withMinute(0)
            time = time.withSecond(0)
            return time
        }

        override fun calculateEndDateTime(): LocalDateTime {
            return LocalDateTime.now()
        }
    },
    YESTERDAY("Yesterday"){
        override fun calculateStartDateTime(): LocalDateTime {
            var time = LocalDateTime.now()
            time = time.withHour(0)
            time = time.withMinute(0)
            time = time.withSecond(0)
            time = time.minusDays(1)
            return time
        }

        override fun calculateEndDateTime(): LocalDateTime {
            var time = LocalDateTime.now()
            time = time.withHour(0)
            time = time.withMinute(0)
            time = time.withSecond(0)
            return time
        }
    },
    WEEK("Week (7d)"){
        override fun calculateStartDateTime(): LocalDateTime {
            var time = LocalDateTime.now()
            time = time.minusDays(7)
            return time
        }

        override fun calculateEndDateTime(): LocalDateTime {
            return LocalDateTime.now()
        }
    },
    MONTH("Month (30d)"){
        override fun calculateStartDateTime(): LocalDateTime {
            var time = LocalDateTime.now()
            time = time.minusDays(30)
            return time
        }

        override fun calculateEndDateTime(): LocalDateTime {
            return LocalDateTime.now()
        }
    },
    ALLTIME("All Time"){
        override fun calculateStartDateTime(): LocalDateTime {
            return LocalDateTime.MIN
        }

        override fun calculateEndDateTime(): LocalDateTime {
            return LocalDateTime.now()
        }
    };

    abstract fun calculateStartDateTime(): LocalDateTime
    abstract fun calculateEndDateTime(): LocalDateTime
}