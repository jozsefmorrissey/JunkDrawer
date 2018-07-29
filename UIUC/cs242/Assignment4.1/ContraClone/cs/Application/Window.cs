using System;
using System.Windows.Forms;
using System.Drawing;
using System.Threading;
using System.Drawing.Drawing2D;

//TODO: Repair initial background size error.
namespace ContraClone
{
	public class Window : Form
	{
		public Graphics display;
		public static System.Windows.Forms.Timer timer;
		public static bool rePaint = true;

		public Window () : base()
		{

			timer = new System.Windows.Forms.Timer ();
			timer.Interval = 15;
			timer.Tick += GameControl.keyTimerCallback;
			timer.Start ();

			this.KeyDown += GameControl.keyDownEvent;
			this.KeyUp += GameControl.keyUpEvent;

			Build ();
			//display = this.CreateGraphics ();
		}

		public void Build()
		{
			this.WindowState = FormWindowState.Maximized;

			this.Text = "Contra Clone 5000";

			this.ResizeRedraw = true;
			this.HelpButton = true;
			this.MaximizeBox = true;
			this.MinimizeBox = true;
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			if(rePaint)
			{
				Graphics formGraphics;
				formGraphics = this.CreateGraphics();

				e.Graphics.SmoothingMode = SmoothingMode.AntiAlias;

				Init.scene.paint (formGraphics);
				//Thread.Sleep (1);

				base.OnPaint (e);

				formGraphics.Dispose();
			}
		}

		protected override void OnResize (EventArgs e)
		{
			Init.scene.bkgd.calculatePosition (Init.scene.view_target);
		}

	}
}

