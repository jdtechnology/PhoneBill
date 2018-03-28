# PhoneBill
This was a simple ask as defined by:

>Your monthly phone bill has just arrived, and it's unexpectedly large. You decide to verify the amount by recalculating
>the bill based on your phone call logs and the phone company's charges.

>The logs are given as a string S consisting of N lines separated by end-of-line characters (ASCII code 10). Each line
>describes one phone call using the following format: "hhzmrn: ss,nnn-nnn-nnn", where "hh zmmzss" denotes the duration
>of the call (in "hh" hours, "mm" minutes and "55" seconds) and "nnn-nnn-nnn" denotes the 9-digit phone number of
>the recipient (with no leading zeros).

>Each call is billed separately. The billing rules are as follows:
>If the call was shorter than 5 minutes, then you pay 3 cents for every started second of the call (e.g.
>for duration "00:01:07" you pay 67 * 3 = 201 cents).

>If the call was at least 5 minutes long, then you pay 150 cents for every started minute of the call
>(e.g. for duration "00:05:00" you pay 5 * 150 = 750 cents and for duration "00:05:01" you pay 6
>150 = 900 cents).

>All calls to the phone number that has the longest total duration of calls are free. In the case of a tie,
>if more than one phone number shares the longest total duration, the promotion is applied only to
>the phone number whose numerical value is the smallest among these phone numbers.

>Write a function:
>object Solution { def solution(S: String): Int
>that, given a string S describing phone call logs, returns the amount of money you have to pay in cents.
>For example, given string S with N = 3 lines:
```
"00:01:07,406-234-090
00:05:01,701-080-080
00:05:00,406-234-090"
```
>the function should return 900 (the total duration for number 400-234-090 is 6 minutes 7 seconds, and the total
>duration for number 701-080-080 is 5 minutes 1 second; therefore, the free promotion applies to the former phone
>number).

>Assume that:

>N is an integer within the range [1..100];

>every phone number follows the format "nnn-nnn-nnn" strictly; there are no leading zeros;

>the duration of every call follows the format "hhzmrn: ss" strictly;

>each line follows the format "hhzmrn: ss,nnn-nnn-nnn" strictly; there are no empty lines and spaces.

>In your solution, focus on correctness. The performance of your solution will not be the focus of the assessment.

## Running:

Just the app
```sbtshell
sbt run
```

Just the tests
```sbtshell
sbt test
```

## Author

 [**Jack Fisher**](https://github.com/jdtechnology/)