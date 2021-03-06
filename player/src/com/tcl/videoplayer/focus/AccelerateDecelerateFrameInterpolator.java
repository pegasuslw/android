package com.tcl.videoplayer.focus;


import android.view.animation.Interpolator;

public class AccelerateDecelerateFrameInterpolator
  implements Interpolator
{
  private double mCoef;
  private float mExponent = 2.0F;
  private float mScale = 20.0F;
  
  public AccelerateDecelerateFrameInterpolator()
  {
    init();
  }
  
  public AccelerateDecelerateFrameInterpolator(float scale, float exponent)
  {
    this.mScale = scale;
    this.mExponent = exponent;
    init();
  }
  
  private void init()
  {
    this.mCoef = (1.0D / Math.atan(this.mScale));
  }
  
  public float getInterpolation(float paramFloat)
  {
    return (float)(Math.atan(Math.pow(paramFloat, this.mExponent) * this.mScale) * this.mCoef);
  }
}
