# Raw use of parameterized class 'Comparator' 

change 

```java
Comparator comparator = new Comparator<File>() {
    @Override
    public int compare(File o1, File o2) {
        return o1.getName().compareTo(o2.getName());
    }
};


Comparator comparator = (Comparator<File>) (o1, o2) -> o1.getName().compareTo(o2.getName());
```

to 

```java
Comparator<File> comparator = new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };

Comparator<File> comparator = (o1, o2) -> o1.getName().compareTo(o2.getName());
```



泛型擦除

