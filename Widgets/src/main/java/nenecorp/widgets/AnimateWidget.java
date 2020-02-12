package nenecorp.widgets;

import android.app.Activity;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Timer;
import java.util.TimerTask;

public class AnimateWidget {
    public static void fadeIn(View x, int duration) {
        YoYo.with(Techniques.FadeIn)
                .duration(duration)
                .repeat(0)
                .playOn(x);
    }

    public static void fadeInUp(View x, int duration) {
        YoYo.with(Techniques.FadeInUp)
                .duration(duration)
                .repeat(0)
                .playOn(x);
    }

    public static void fadeInDown(View x, int duration) {
        YoYo.with(Techniques.FadeInDown)
                .duration(duration)
                .repeat(0)
                .playOn(x);
    }

    public static void fadeInRight(View x, int duration) {
        YoYo.with(Techniques.FadeInRight)
                .duration(duration)
                .repeat(0)
                .playOn(x);
    }

    public static void fadeInLeft(View x, int duration) {
        YoYo.with(Techniques.FadeInLeft)
                .duration(duration)
                .repeat(0)
                .playOn(x);
    }

    public static void fadeOut(View x, int duration) {
        YoYo.with(Techniques.FadeOut)
                .duration(duration)
                .repeat(0)
                .playOn(x);
    }

    public static void slideOutLeft(View x, int duration) {
        YoYo.with(Techniques.SlideOutLeft)
                .duration(duration)
                .repeat(0)
                .playOn(x);

    }

    public static void slideInLeft(View x, int duration) {
        YoYo.with(Techniques.SlideInLeft)
                .duration(duration)
                .repeat(0)
                .playOn(x);
    }

    public static void slideOutRight(View x, int duration) {
        YoYo.with(Techniques.SlideOutRight)
                .duration(duration)
                .repeat(0)
                .playOn(x);

    }

    public static void slideInRight(View x, int duration) {
        YoYo.with(Techniques.SlideInRight)
                .duration(duration)
                .repeat(0)
                .playOn(x);
    }

    public static void slideInUp(View x, int duration) {
        YoYo.with(Techniques.SlideInUp)
                .duration(duration)
                .repeat(0)
                .playOn(x);

    }

    public static void slideOutUp(View x, int duration) {
        YoYo.with(Techniques.SlideOutUp)
                .duration(duration)
                .repeat(0)
                .playOn(x);

    }

    public static void slideInDown(View x, int duration) {
        YoYo.with(Techniques.SlideInDown)
                .duration(duration)
                .repeat(0)
                .playOn(x);
    }

    public static void slideOutDown(View x, int duration) {
        YoYo.with(Techniques.SlideOutDown)
                .duration(duration)
                .repeat(0)
                .playOn(x);

    }

    public static void shakeView(View x, int duration) {
        YoYo.with(Techniques.Shake)
                .duration(duration)
                .repeat(0)
                .playOn(x);
    }

    public static void fadeIn(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.FadeIn)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);
    }

    public static void fadeInUp(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.FadeInUp)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);
    }

    public static void fadeInDown(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.FadeInDown)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);
    }

    public static void fadeInRight(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.FadeInRight)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);
    }

    public static void fadeInLeft(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.FadeInLeft)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);
    }

    public static void fadeOut(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.FadeOut)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);
    }

    public static void slideOutLeft(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.SlideOutLeft)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);

    }

    public static void slideInLeft(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.SlideInLeft)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);
    }

    public static void slideOutRight(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.SlideOutRight)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);

    }

    public static void slideInRight(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.SlideInRight)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);
    }

    public static void slideInUp(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.SlideInUp)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);

    }

    public static void slideOutUp(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.SlideOutUp)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);

    }

    public static void slideInDown(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.SlideInDown)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);
    }

    public static void slideOutDown(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.SlideOutDown)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);

    }

    public static void shakeView(final View x, int duration, final AnimationEventListener animationEventListener) {
        YoYo.with(Techniques.Shake)
                .duration(duration)
                .repeat(0)
                .playOn(x);
        new Timer()
                .schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity) x.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                animationEventListener.onComplete(x);
                            }
                        });
                    }
                }, duration);
    }
}
