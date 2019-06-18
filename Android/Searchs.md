# Google Search

## Camera preview 模糊

- 原因：未对焦

- 解决：设置自动对焦

  ```kotlin
  private fun setCameraFocusMode() {
      camera?.let {
          /* Set Auto focus */
          val parameters = it.parameters
          val focusModes = parameters.supportedFocusModes
          focusModes.iterator().forEach { i ->
              Log.d(TAG, " mode $i")
          }
          if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
              parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
          } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
              parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
          } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
              parameters.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
          } else {
              parameters.focusMode = Camera.Parameters.FOCUS_MODE_FIXED
          }
          it.parameters = parameters
      }
  }
  ```

   

## SurfaceView 显示实际像素数量

- 解决:直接设置宽高

  ```kotlin
  private fun reSizeSurfaceView(size: Camera.Size) {
      val layoutParams = surfaceView.layoutParams
      layoutParams.width = size.height
      layoutParams.height = size.width
      surfaceView.layoutParams = layoutParams
  }
  ```



## Spinner 微调框

- New knowledge

  ```java
   public void onItemSelected(AdapterView<?> parent, View view,
                              int pos, long id) {
       Object item =  parent.getItemAtPosition(pos)
   }
  ```

  ```java
  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
          R.array.planets_array, android.R.layout.simple_spinner_item);
  ```


