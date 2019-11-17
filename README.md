# hoolah

This system reports the total number of transactions and the average transaction value for a specific merchant in provided date range.
Additionally, if a transaction record has a REVERSAL transaction, then it does not include in the computed statistics, even if the reversing transaction is outside of the requested date range.

Run instructions: 
1. Please checkout the project and import into InteliJ IDE. Open the TransactionServiceTest.java and run it as Junit test case. 
   It contains 3 test cases.
2. To run with the custom csv file, make a little change in the setUp method of the test file.
   Please provide the absolute path to csv file as parameter in the DataLoader.init method which is being called in the setUp method.

This project comprises of two main classes:
1. DataLoader: It reads the CSV and organize the data into the efficient format so that it can calculate the reports in optimized manner.
2. TransactionService: This takes the input and fetch the data from DataLoader class and performs the required operation.

Following is the assumptions with data loading in the system:
1. All the fields except "Related Transaction Id" are mandatory. If any of the field will be missing the data loading will fail with proper error message.
2. The data should be in sorted order of "Transaction Date".
3. The "Transaction date" should be in "DD/MM/YYYY hh:mm:ss" format.
4. The allowed values of "Transaction Type" are "PAYMENT" and "REVERSAL".
5. !!Most important!! The "reversal" transaction should always follow "payment" transaction i.e. a payment cannot be revered if it has not performed in the past.
