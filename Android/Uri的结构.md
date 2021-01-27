### Uri的结构

`[scheme:]scheme-specific-part[#fragment]`

`[scheme:][//authority][path][?query][#fragment]`

`[scheme:][//host:port][path][?query][#fragment]`

```xml
<activity
    android:name=".SecondActivity"
    android:label="@string/title_activity_second">
    <intent-filter>
        <action android:name="android.qijian.schemeurl.activity" />
        <category android:name="android.intent.category.DEFAULT" />
        <data
            android:scheme="qijian"
            android:host="test.uri.activity" />
    </intent-filter>
</activity>
```

## Syntax of URL 统一资源定位符



Here is a Syntax of URL:

**http://www.domainname.com/folder-name/web page-file-name.htm**

We can divide the above URL into the following parts:

- **Protocol:** It is the first part of the URL. Here, the protocol name is Hypertext Transfer Protocol (HTTP).
- **http://www.domainname.com/:** It is your domain name. It is also known as server id or the host.
- **/folder-name/:** It indicates that the website page referenced in "filed" in a given folder on the webserver.
- **web-page-file-name.htm:** It is actually a web page file name. The ".htm" is an extension for the HTML file, which shows that it is a static web page. File names can have different extensions or it is depend on how you have set up a web server. There could be no extension at all, and the URL could end with a slash line (/).

## Syntax of URI 统一资源标识符

Here is a syntax of URI:

**URI = scheme:[//authority]path[?query][#fragment]**

The URI includes the following parts:

- Scheme component

  : It is a non-empty component followed by a colon (:). Scheme contains a sequence of characters starting with a letter and followed by any combination of digits, letters, period (.), hyphen (-), or plus (+).

  Examples of well-known schemes include HTTP, HTTPS, mailto, file, FTP, etc. URI schemes must be registered with the Internet Assigned Numbers Authority (IANA).

- **Authority component:** It is an optional field and is preceded by //. It consists of

1. Optional userinfo subcomponent that might consist of a username and password (optional).
2. A host subcomponent containing either an IP address or a registered name.
3. An optional port subcomponent that is followed by a colon (:)

- **Path:** A path contains a sequence of segments that are separated by a slash.
- **Query component:** It is optional and preceded by a question mark (?). Query component contains a query string of non-hierarchical data.
- **Fragment component:** It is an optional field and preceded by a hash (#). Fragment component includes a fragment identifier giving direction to a secondary resource.

## URL Vs. URI

Here are the main differences between URL and URI:

| **URL**                                                      | **URI**                                                      |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| URL stands for Uniform Resource Locator.                     | URI stands for Uniform Resource Identifier.                  |
| URL is a subset of URI that specifies where a resource is exists and the mechanism for retrieving it. | A URI is a superset of URL that identifies a resource either by URL or URN (Uniform Resource Name) or both. |
| The main aim is to get the location or address of a resource | The main aim of URI is to find a resource and differentiate it from other resources using either name or location. |
| URL is used to locate only web pages                         | Used in HTML, XML and other files XSLT (Extensible Stylesheet Language Transformations) and more. |
| The scheme must be a protocol like HTTP, FTP, HTTPS, etc.    | In URI, the scheme may be anything like a protocol, specification, name, etc. |
| Protocol information is given in the URL.                    | There is no protocol information given in URI.               |
| Example of URL: [https://google.com](https://google.com/)    | Example of URI: urn:isbn:0-486-27557-4                       |
| It contains components such as protocol, domain, path, hash, query string, etc. | It contains components like scheme, authority, path, query, fragment component, etc. |
| All URLs can be URIs                                         | Not all URIs are URLs since a URI can be a name instead of a locator. |

