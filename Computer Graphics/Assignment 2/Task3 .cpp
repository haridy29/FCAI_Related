
/*
Mohamed Ali Mohamed 20190466
Mahmoud Gamal	    20190502
*/


#include <Windows.h>
#include <gl\GL.h>
#include <gl\GLu.h>
#include <cmath>
using namespace std;
#pragma comment(lib, "opengl32")
#pragma comment(lib, "glu32")

struct Vector {
    double v[2];

    Vector(double x = 0, double y = 0) {
        v[0] = x;
        v[1] = y;
    }

    double operator[](int idx) { return v[idx]; }
};

int Round(double x) {
    return (int) (x + 0.5);
}

void DrawHermiteCurve(HDC hdc, Vector &p1, Vector &T1, Vector &p2, Vector &T2, Vector center, Vector R) {
    int dx = R[0] - center[0];
    int dy = R[1] - center[1];
    int r = dx * dx + dy * dy;

    double a0 = p1[0], a1 = T1[0], a2 = -3 * p1[0] - 2 * T1[0] + 3 * p2[0] - T2[0],
            a3 = 2 * p1[0] + T1[0] - 2 * p2[0] + T2[0];
    double b0 = p1[1], b1 = T1[1], b2 = -3 * p1[1] - 2 * T1[1] + 3 * p2[1] - T2[1],
            b3 = 2 * p1[1] + T1[1] - 2 * p2[1] + T2[1];
    for (double t = 0; t <= 1; t += 0.001) {
        double tt = t * t, ttt = tt * t;
        double x = a0 + a1 * t + a2 * tt + a3 * ttt;
        double y = b0 + b1 * t + b2 * tt + b3 * ttt;
        dx = Round(x) - center[0];
        dy = Round(y) - center[1];
        int dist = dx * dx + dy * dy;
        if (dist > r)
            SetPixel(hdc, Round(x), Round(y), RGB(255, 0, 0));
        else
            SetPixel(hdc, Round(x), Round(y), RGB(0, 0, 255));

    }


}

void swap(int &x1, int &y1, int &x2, int &y2) {
    int tmp = x1;
    x1 = x2;
    x2 = tmp;
    tmp = y1;
    y1 = y2;
    y2 = tmp;
}


void Draw8Points(HDC hdc, int xc, int yc, int x, int y, COLORREF c) {
    SetPixel(hdc, xc + x, yc + y, c);
    SetPixel(hdc, xc + x, yc - y, c);
    SetPixel(hdc, xc - x, yc - y, c);
    SetPixel(hdc, xc - x, yc + y, c);
    SetPixel(hdc, xc + y, yc + x, c);
    SetPixel(hdc, xc + y, yc - x, c);
    SetPixel(hdc, xc - y, yc - x, c);
    SetPixel(hdc, xc - y, yc + x, c);
}

void DrawCircle(HDC hdc, int xc, int yc, int rx, int ry, COLORREF c) {

    int dx = rx - xc;
    int dy = ry - yc;
    double R = sqrt(dx * dx + dy * dy);

    int x = 0;
    double y = R;
    Draw8Points(hdc, xc, yc, 0, Round(R), c);
    double d = 1 - R;
    int d1 = 3;
    double d2 = 5 - 2 * R;
    while (x < y) {
        x++;
        if (d < 0) {
            d += d1;
            d2 += 2;
        } else {
            y--;
            d += d2;
            d2 += 4;
        }
        d1 += 2;
        Draw8Points(hdc, xc, yc, x, Round(y), c);
    }
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

static Vector Points[4];
static Vector cir[2];
static int idx = 0;
static int ciridx = 0;

LRESULT WINAPI MyWndProc(HWND hwnd, UINT mcode, WPARAM wp, LPARAM lp) {
    static HDC hdc;
    static HGLRC glrc;
    switch (mcode) {
        case WM_CREATE:
            hdc = GetDC(hwnd);
            glrc = InitOpenGl(hdc);
            break;
        case WM_SIZE:
            AdjustWindowFor2D(hdc, LOWORD(lp), HIWORD(lp));
            break;

        case WM_LBUTTONDOWN:
            cir[ciridx++] = Vector(LOWORD(lp), HIWORD(lp));
            if (ciridx == 2) {
                hdc = GetDC(hwnd);
                DrawCircle(hdc, (int) cir[0][0], (int) cir[0][1], (int) cir[1][0], (int) cir[1][1], RGB(0, 255, 0));
                ReleaseDC(hwnd, hdc);
                ciridx = 0;

            }
            break;
        case WM_RBUTTONDOWN:
            Points[idx++] = Vector(LOWORD(lp), HIWORD(lp));
            if (idx == 4) {
                Vector T1(3 * (Points[1][0] - Points[0][0]), 3 * (Points[1][1] - Points[0][1]));
                Vector T2(3 * (Points[3][0] - Points[2][0]), 3 * (Points[3][1] - Points[2][1]));
                hdc = GetDC(hwnd);
                DrawHermiteCurve(hdc, Points[0], T1, Points[3], T2, cir[0], cir[1]);
                idx = 0;
                ReleaseDC(hwnd, hdc);
            }

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
    wc.hbrBackground = (HBRUSH) GetStockObject(LTGRAY_BRUSH);
    wc.hCursor = LoadCursor(NULL, IDC_ARROW);
    wc.hIcon = LoadIcon(NULL, IDI_APPLICATION);
    wc.hInstance = hinst;
    wc.lpfnWndProc = MyWndProc;
    wc.lpszClassName = "MyClass";
    wc.lpszMenuName = NULL;
    wc.style = CS_HREDRAW | CS_VREDRAW;
    RegisterClass(&wc);
    HWND hwnd = CreateWindow("MyClass", "Task3", WS_OVERLAPPEDWINDOW | WS_CLIPCHILDREN | WS_CLIPSIBLINGS, 0,
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
