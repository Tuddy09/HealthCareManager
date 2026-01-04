# 🎬 Animation Reference Guide

## Quick Reference for All Animations in HealthCare Manager

---

## 🎯 Animation Types

### 1. **Entrance Animations**

#### Fade In
```kotlin
AnimatedVisibility(
    visible = isVisible,
    enter = fadeIn(animationSpec = tween(600))
)
```
- **Duration:** 600ms
- **Used in:** All screens for initial content appearance
- **Effect:** Smooth opacity transition from 0 to 1

#### Slide In Vertical
```kotlin
slideInVertically(
    initialOffsetY = { it / 3 },
    animationSpec = tween(400)
)
```
- **Duration:** 400ms
- **Offset:** 1/3 of screen height
- **Used in:** Form screens, card layouts
- **Effect:** Slides content from bottom to position

#### Slide In Horizontal
```kotlin
slideInHorizontally(
    initialOffsetX = { -it },
    animationSpec = tween(400)
)
```
- **Duration:** 400ms
- **Offset:** Full width (left or right)
- **Used in:** List items, reminders
- **Effect:** Slides content from side

#### Scale In
```kotlin
scaleIn(
    initialScale = 0.8f,
    animationSpec = tween(400)
)
```
- **Duration:** 400ms
- **Initial Scale:** 0.8
- **Used in:** Menu cards, grid items
- **Effect:** Grows from center

---

### 2. **Logo/Hero Animations**

#### Bouncy Scale
```kotlin
val scale by animateFloatAsState(
    targetValue = if (isVisible) 1f else 0.3f,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    ),
    label = "scale"
)
```
- **Damping:** Medium Bouncy
- **Stiffness:** Low
- **Used in:** Login/Register headers
- **Effect:** Spring-based scaling with bounce

---

### 3. **Interaction Animations**

#### Button Press
```kotlin
val scale by animateFloatAsState(
    targetValue = if (isPressed) 0.95f else 1f,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
)
```
- **Pressed Scale:** 0.95
- **Normal Scale:** 1.0
- **Used in:** Menu cards, clickable items
- **Effect:** Subtle press feedback

---

### 4. **Staggered List Animations**

#### With Delay
```kotlin
itemsIndexed(items) { index, item ->
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(index * 80L)
        isVisible = true
    }
    
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInHorizontally()
    )
}
```
- **Delay per item:** 50-100ms
- **Used in:** All list screens
- **Effect:** Sequential appearance

---

## 🎨 Animation Combinations

### Login Screen
```kotlin
fadeIn(tween(800)) + slideInVertically({ it / 2 })
```
- Fades in while sliding from middle

### Home Screen Cards
```kotlin
fadeIn(tween(400)) + scaleIn(0.8f, tween(400))
```
- Fades in while scaling up

### Medical Records
```kotlin
fadeIn(tween(400)) + slideInVertically({ it / 2 }) + expandVertically(tween(400))
```
- Fades, slides, and expands simultaneously

---

## ⏱️ Timing Guide

| Duration | Use Case |
|----------|----------|
| 300ms | Quick interactions, button presses |
| 400ms | Standard transitions, list items |
| 600ms | Screen content, cards |
| 800ms | Hero elements, headers |

---

## 🎭 Animation States

### LaunchedEffect Pattern
```kotlin
var isVisible by remember { mutableStateOf(false) }

LaunchedEffect(Unit) {
    isVisible = true
}
```
- Triggers animation once on composition

### Delayed Trigger
```kotlin
LaunchedEffect(Unit) {
    delay(index * 80L)
    isVisible = true
}
```
- Adds stagger delay for lists

---

## 🎪 Special Effects

### Alternating Slide Direction
```kotlin
slideInHorizontally(
    initialOffsetX = { if (index % 2 == 0) -it else it }
)
```
- Even items slide from left
- Odd items slide from right

### Conditional Animation
```kotlin
AnimatedVisibility(
    visible = isVisible,
    enter = fadeIn(tween(600)),
    exit = fadeOut(tween(300))
)
```
- Different timing for enter/exit

---

## 📱 Screen-Specific Animations

### Patient Home
- **Header:** Fade + Slide from top (600ms)
- **Cards:** Staggered scale + fade (100ms delay)
- **Press:** Scale to 0.95 with bounce

### Appointments List
- **Items:** Fade + Slide from right (80ms delay)
- **Status bars:** Instant appearance
- **Empty state:** Fade only

### Medical Records
- **Cards:** Fade + Slide + Expand (100ms delay)
- **Header gradient:** Instant
- **Sections:** Sequential reveal

### Health Tips
- **Items:** Alternating horizontal slides (50ms delay)
- **Icon bubbles:** Instant
- **Content:** Fade with parent

---

## 🔧 Performance Tips

1. **Use remember for animation states**
   ```kotlin
   var isVisible by remember { mutableStateOf(false) }
   ```

2. **Avoid heavy animations in lists**
   - Use simple fade/slide for list items
   - Complex animations only for single elements

3. **Cleanup in LaunchedEffect**
   ```kotlin
   LaunchedEffect(key) {
       // Animation logic
       cleanup()
   }
   ```

4. **Use animationSpec labels**
   ```kotlin
   animateFloatAsState(
       targetValue = value,
       label = "descriptive_name"
   )
   ```

---

## 🎯 Best Practices

✅ **DO:**
- Use subtle, smooth animations
- Match animation duration to importance
- Provide visual feedback for interactions
- Test on different devices

❌ **DON'T:**
- Overuse animations (can be distracting)
- Use very slow animations (>1000ms)
- Animate large images without optimization
- Forget to handle loading states

---

## 🚀 Quick Start

### Basic Entrance Animation
```kotlin
var isVisible by remember { mutableStateOf(false) }

LaunchedEffect(Unit) {
    isVisible = true
}

AnimatedVisibility(
    visible = isVisible,
    enter = fadeIn(tween(600)) + slideInVertically()
) {
    // Your content
}
```

### Basic Press Animation
```kotlin
var isPressed by remember { mutableStateOf(false) }
val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

Card(
    modifier = Modifier
        .scale(scale)
        .clickable {
            isPressed = true
            onClick()
        }
)
```

---

## 📚 Resources

- **Compose Animation Docs:** [developer.android.com/jetpack/compose/animation](https://developer.android.com/jetpack/compose/animation)
- **Material Motion:** [material.io/design/motion](https://material.io/design/motion)
- **Animation Easing:** [easings.net](https://easings.net)

---

**Happy Animating! 🎉**

