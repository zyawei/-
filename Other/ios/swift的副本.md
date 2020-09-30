Error Domain=NSURLErrorDomain Code=-1003 "A server with the specified hostname could not be found

```
The fix here was:

Click on project in project explorer
Select the Signing & Capabilities tab
Check "Network: Outgoing connections (client)" checkbox in the AppSandbox section
```

