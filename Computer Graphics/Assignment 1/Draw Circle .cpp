#include <Windows.h>
#include <gl\GL.h>
#include <gl\GLu.h>
#include <cmath>

#pragma comment(lib, "opengl32")
#pragma comment(lib, "glu32")

void swap(int& x1, int& y1, int& x2, int& y2) {
    int tmp = x1;
    x1 = x2;
    x2 = tmp;
    tmp = y1;
    y1 = y2;
    y2 = tmp;
}

int Round(double x) {
    return (int)(x + 0.5);
}

void DrawLine(int x1, int y1, int x2, int y2) {
    glBegin(GL_POINTS);
    int dx = x2 - x1;
    int dy = y2 - y1;
    glVertex2d(x1, y1);
    if (abs(dx) >= abs(dy))
    {
        int x = x1, xinc = dx > 0 ? 1 : -1;
        double y = y1, yinc = (double)dy / dx * xinc;
        while (x != x2)
        {
            x += xinc;
            y += yinc;
            glVertex2d( x, Round(y));
        }
    }
    else
    {
        int y = y1, yinc = dy > 0 ? 1 : -1;
        double x = x1, xinc = (double)dx / dy * yinc;
        while (y != y2)
        {
            x += xinc;
            y += yinc;
            glVertex2d( Round(x), y);
        }
    }
    glEnd();
    glFlush();
}

void Draw8Points(int xc, int yc, int x, int y) {
    glVertex2d(xc + x, yc + y);
    glVertex2d(xc + x, yc - y);
    glVertex2d(xc - x, yc - y);
    glVertex2d(xc - x, yc + y);
    glVertex2d(xc + y, yc + x);
    glVertex2d(xc + y, yc - x);
    glVertex2d(xc - y, yc - x);
    glVertex2d(xc - y, yc + x);
}

void Draw8Lines(int xc, int yc, int x, int y) {
    glColor3f(0.3, 0.2, 0.4);
    DrawLine(xc, yc, xc + x, yc + y);

    glColor3f(0, 0, 1);
    DrawLine(xc + x, yc - y, xc, yc);

    glColor3f(0, 1, 0);
    DrawLine(xc, yc, xc - x, yc + y);

    glColor3f(0, 1, 1);
    DrawLine(xc - x, yc - y, xc, yc);
    glColor3f(1, 0, 0);

    glColor3f(1, 0, 1);
    DrawLine(xc, yc, xc + y, yc + x);
    glColor3f(1, 1, 0);

    DrawLine(xc - y, yc + x, xc, yc);
    glColor3f(1, 1, 1);

    DrawLine(xc, yc, xc + y, yc - x);
    glColor3f(0.4, 1, 0.1);
    DrawLine(xc - y, yc - x, xc, yc);

}

void DrawCircle(int xc, int yc, int rx, int ry) {
    glBegin(GL_POINTS);
    glColor3f(0, 0, 1);

    int dx = rx - xc;
    int dy = ry - yc;
    double R = sqrt(dx * dx + dy * dy);
    int x = 0;

    double y = R;
    Draw8Points(xc, yc, 0, Round(R));
    Draw8Lines(xc, yc, 0, Round(R));
    double d = 1 - R;

    int d1 = 3;
    double d2 = 5 - 2 * R;


    while (x < y) {
        x++;
        if (d < 0) {
            d += d1;
            d2 += 2;
        }
        else {

            y--;
            d += d2;
            d2 += 4;
        }
        d1 += 2;
        Draw8Points(xc, yc, x, Round(y));
        Draw8Lines(xc, yc, x, Round(y));
    }
    glEnd();
    glFlush();
}


HGLRC InitOpenGl(HDC hdc) {
    PIXELFORMATDESCRIPTOR pfd = {
            sizeof(PIXELFORMATDESCRIPTOR),   // size of this pfd
            1,                     // version number
            PFD_DRAW_TO_WINDOW |   // support window
            PFD_SUPPORT_OPENGL |   // support OpenGL
            PFD_DOUBLEBUFFER,      // double buffered
            PFD_TYPE_RGBA,         // RGBA type
            24,                    // 24-bit color depth
            0, 0, 0, 0, 0, 0,      // color bits ignored
            0,                     // no alpha buffer
            0,                     // shift bit ignored
            0,                     // no accumulation buffer
            0, 0, 0, 0,            // accum bits ignored
            32,                    // 32-bit z-buffer
            0,                     // no stencil buffer
            0,                     // no auxiliary buffer
            PFD_MAIN_PLANE,        // main layer
            0,                     // reserved
            0, 0, 0                // layer masks ignored
    };
    int iPixelFormat;
    iPixelFormat = ChoosePixelFormat(hdc, &pfd);
    SetPixelFormat(hdc, iPixelFormat, &pfd);
    HGLRC glrc = wglCreateContext(hdc);
    wglMakeCurrent(hdc, glrc);
    return glrc;
}

void AdjustWindowFor2D(HDC hdc, int w, int h) {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0, w, 0, h);
    glMatrixMode(GL_MODELVIEW);
    glViewport(0, 0, w, h);
    glClearColor(0, 0, 0, 0);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    SwapBuffers(hdc);
}

void EndOpenGl(HGLRC glrc) {
    wglMakeCurrent(NULL, NULL);
    wglDeleteContext(glrc);
}

int xc = -1, yc = -1;

LRESULT WINAPI MyWndProc(HWND hwnd, UINT mcode, WPARAM wp, LPARAM lp) {
    static HDC hdc;
    static HGLRC glrc;
    int Rx, Ry;

    switch (mcode) {
    case WM_CREATE:
        hdc = GetDC(hwnd);
        glrc = InitOpenGl(hdc);
        break;
    case WM_SIZE:
        AdjustWindowFor2D(hdc, LOWORD(lp), HIWORD(lp));
        break;

    case WM_LBUTTONDOWN:
        xc = LOWORD(lp);
        yc = HIWORD(lp);
        break;
    case WM_RBUTTONDOWN:

        Rx = LOWORD(lp);
        Ry = HIWORD(lp);
        if (xc == -1)break;
        DrawCircle(xc, yc, Rx, Ry);
        glFlush();
        SwapBuffers(hdc);
        break;


    case WM_CLOSE:
        DestroyWindow(hwnd);
        break;
    case WM_DESTROY:
        EndOpenGl(glrc);
        ReleaseDC(hwnd, hdc);
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(hwnd, mcode, wp, lp);
    }
    return 0;
}

int APIENTRY WinMain(HINSTANCE hinst, HINSTANCE pinst, LPSTR cmd, int nsh) {
    WNDCLASS wc;
    wc.cbClsExtra = wc.cbWndExtra = 0;
    wc.hbrBackground = (HBRUSH)GetStockObject(LTGRAY_BRUSH);
    wc.hCursor = LoadCursor(NULL, IDC_ARROW);
    wc.hIcon = LoadIcon(NULL, IDI_APPLICATION);
    wc.hInstance = hinst;
    wc.lpfnWndProc = MyWndProc;
    wc.lpszClassName = L"MyClass";
    wc.lpszMenuName = NULL;
    wc.style = CS_HREDRAW | CS_VREDRAW;
    RegisterClass(&wc);
    HWND hwnd = CreateWindow(L"MyClass", L"Task3", WS_OVERLAPPEDWINDOW | WS_CLIPCHILDREN | WS_CLIPSIBLINGS, 0,
        0, 800, 600, NULL, NULL, hinst, 0);
    ShowWindow(hwnd, nsh);
    UpdateWindow(hwnd);
    MSG msg;
    while (GetMessage(&msg, NULL, 0, 0) > 0) {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }
    return 0;
}
